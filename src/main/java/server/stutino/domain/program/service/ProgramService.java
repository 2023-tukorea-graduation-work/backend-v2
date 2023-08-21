package server.stutino.domain.program.service;


import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.stutino.domain.member.entity.Member;
import server.stutino.domain.member.repository.MemberRepository;
import server.stutino.domain.program.dto.request.ProgramParticipateRequest;
import server.stutino.domain.program.dto.request.ProgramRegisterRequest;
import server.stutino.domain.program.dto.response.ProgramDetailResponse;
import server.stutino.domain.program.dto.response.ProgramListResponse;
import server.stutino.domain.program.entity.*;
import server.stutino.domain.program.exception.ParticipantsDuplicateException;
import server.stutino.domain.program.mapper.ProgramMapper;
import server.stutino.domain.program.repository.ParticipantsRepository;
import server.stutino.domain.program.repository.ProgramCategoryRepository;
import server.stutino.domain.program.repository.ProgramRepository;
import server.stutino.domain.program.repository.ProgramWeekRepository;
import server.stutino.util.CustomDateUtil;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProgramService {

     ProgramRepository programRepository;
     ProgramWeekRepository programWeekRepository;
     ProgramCategoryRepository programCategoryRepository;
     ParticipantsRepository programParticipationRepository;
     MemberRepository memberRepository;
     ProgramMapper programMapper;
     CustomDateUtil customDateUtil;

    /*
     * 프로그램 등록
     * */
    @Transactional
    public void registerProgram(ProgramRegisterRequest request) {
        // [1] 프로그램 기본 정보 등록
        Member mentor = memberRepository.findById(request.getMemberId())
                 .orElseThrow(RuntimeException::new);

        // [1-1] 프로그램 등록
        Program program = Program.builder()
                .member(mentor)
                .subject(request.getSubject())
                .detail(request.getDetail())
                .programStartDate(customDateUtil.convertStringToLocalDate(request.getProgramStartDate()))
                .programFinishDate(customDateUtil.convertStringToLocalDate(request.getProgramFinishDate()))
                .recruitStartDate(customDateUtil.convertStringToLocalDate(request.getRecruitStartDate()))
                .recruitFinishDate(customDateUtil.convertStringToLocalDate(request.getRecruitFinishDate()))
                .capacity(request.getCapacity())
                .programPlace(request.getProgramPlace())
                .programState(ProgramState.RECRUIT)
                .build();

        programRepository.save(program);

        if(request.getProgramWeeks().size() > 0) {
            List<ProgramWeek> programWeeks = new ArrayList<>();
            request.getProgramWeeks().forEach(dto -> {
                programWeeks.add(new ProgramWeek(
                        program,
                        dto.getContent(),
                        dto.getRegisterDate()
                ));
            });
            programWeekRepository.saveAll(programWeeks);
        }

        if(request.getProgramCategories().size() > 0) {
            List<ProgramCategory> programCategories = new ArrayList<>();
            request.getProgramCategories().forEach(dto -> {
                programCategories.add(new ProgramCategory(
                        program,
                        dto.getParent(),
                        dto.getChild()
                ));
            });
            programCategoryRepository.saveAll(programCategories);
        }
    }

    /*
     * 프로그램 목록 조회
     * */
    public List<ProgramListResponse> getProgramList(String keyword) {
        return programRepository.getProgramList(keyword);
    }

    /*
    * 프로그램 상세 조회
    * */
    public ProgramDetailResponse getProgramDetail(Long programId) {
        ProgramDetailResponse response = programRepository.getProgramDetail(programId);
        List<ProgramWeek> programWeeks = programWeekRepository.getProgramWeekByProgramId(programId);
        response.setProgramWeeks(programWeeks.stream().map(programMapper::toProgramWeekDetailDto).toList());
        return response;
    }

    /*
    * 프로그램 참여 정보 등록
    * */
    public void registerParticipation(ProgramParticipateRequest request) {
        if(programParticipationRepository.isParticipated(request.getProgramId(), request.getMenteeId()) > 0) {
            throw new ParticipantsDuplicateException();
        }
        else {
            programParticipationRepository.save(Participants.builder()
                    .program(programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new))
                    .member(memberRepository.findById(request.getMenteeId()).orElseThrow(EntityNotFoundException::new))
                    .build());
        }
    }

    /*
     * 프로그램 삭제
     * */
    public void deleteProgram(Long programId) {
        programRepository.deleteById(programId);
    }

    /*
    * pdf download
    * */
    @Transactional
    public void downloadPdf(Long programId) {
        String filepath = "/Users/jeongminchang/Desktop/repo/2023-tukorea-graduation-mento/pdf";
        String fontpath = "/Users/jeongminchang/Desktop/repo/2023-tukorea-graduation-mento/font1.ttf";

        try {
            // [1] Creating a Pdf Writer object
            PdfWriter writer = new PdfWriter(filepath);

            // [2] Creating a PdfDocument object
            PdfDocument pdfDocument = new PdfDocument(writer);

            // [3] Adding an empth page
            pdfDocument.addNewPage();

            // [4] Creating a Document object
            Document document = new Document(pdfDocument);

            // [4-1] Program의 주차별 만큼 page를 분할 할지 아니면 한 페이지에 다 담을지는 추후에 결정
            // 분할 코드
//            AreaBreak area = new AreaBreak();
//            document.add(area);

            PdfFont font = PdfFontFactory.createFont(fontpath, PdfEncodings.IDENTITY_H, true);

            // [5] header component 구성
            // [프로그램 주제, 활동기간, 멘토명, 멘토학교 및 학과, 프로그램 분류(카테고리) 정보 기입]
            Program program = programRepository.findById(programId).orElseThrow(EntityNotFoundException::new);

            Paragraph header = new Paragraph("01. " + program.getSubject())
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                    .setFontSize(14)
                    .setFontColor(ColorConstants.RED)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(header);

            Paragraph detail = new Paragraph(program.getDetail())
                    .setFont(font);
            document.add(detail);

            String mentorInfo = "멘토명: " + program.getMember().getName() + "\n"
                        + "출신학교: "+ program.getMember().getInstitution() + "\n"
                        + "전공: " + program.getMember().getMajor() + "\n"
                        + "활동기간: " + program.getProgramStartDate() + "~" + program.getProgramFinishDate();

            Paragraph mentor = new Paragraph(mentorInfo)
                    .setFont(font);
            document.add(mentor);

            // [6] body component 구성
            Paragraph body = new Paragraph("02. 학습 계획 및 활동")
                    .setFont(font)
                    .setFontSize(14)
                    .setFontColor(ColorConstants.RED)
                    .setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(body);

            // [6-1] Adding Table
            float [] pointColumnWidths = {100F, 800F};
            Table table = new Table(pointColumnWidths);

            // Adding cells to the table
            // cell 순서 [Plan, Details, Notes,
            Cell cell1 = new Cell(4, 1); // 2행 1열로 통합
            cell1.add(new Paragraph("첫 번째 행 첫 번째 열 (통합)").setFont(font));
            table.addCell(cell1);

            // 첫 번째 행 두 번째 열
            Cell cell2 = new Cell();
            cell2.add(new Paragraph("첫 번째 행 두 번째 열").setFont(font));
            table.addCell(cell2);

            // 두 번째 행 첫 번째 열 (통합)
            Cell cell3 = new Cell();
            cell3.add(new Paragraph("두 번째 행 첫 번째 열 (통합)").setFont(font));
            table.addCell(cell3);

            // 두 번째 행 두 번째 열
            Cell cell4 = new Cell();
            cell4.add(new Paragraph("두 번째 행 두 번째 열").setFont(font));
            table.addCell(cell4);

            document.add(table);

            document.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


