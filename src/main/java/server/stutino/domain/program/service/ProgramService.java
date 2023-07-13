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
import server.stutino.domain.program.entity.Participants;
import server.stutino.domain.program.entity.Program;
import server.stutino.domain.program.entity.ProgramCategory;
import server.stutino.domain.program.entity.ProgramWeek;
import server.stutino.domain.program.exception.ParticipantsDuplicateException;
import server.stutino.domain.program.mapper.ProgramMapper;
import server.stutino.domain.program.repository.ParticipantsRepository;
import server.stutino.domain.program.repository.ProgramCategoryRepository;
import server.stutino.domain.program.repository.ProgramRepository;
import server.stutino.domain.program.repository.ProgramWeekRepository;
import server.stutino.util.CustomDateUtil;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
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

        /*
         * 문제1. programMapper.toEntity(request, mentor, programWeeks)를 하면 program.id 가 null이 아님.
         * programRepository.save(program); 시 insert전에 select를 먼저 함. 이때 일치하는 program 정보가 있는 탓인지 insert를 안하는 문제가 있음.
         * 문제2. program_week 테이블의 program_id(fk)도 insert되지 않는 문제가 있다.
         *
         * 해결1 : select -> insert문제는 해결 못함
         * 해결2 : program_week 테이블의 program_id(fk)도 insert되지 않는 문제는 saveAll로 해결
         * */
        // [1-1] 프로그램 등록
        Program program = programRepository.save(
                Program.builder()
                    .member(mentor)
                    .subject(request.getSubject())
                        .detail(request.getDetail())
                        .programStartDate(customDateUtil.convertStringToLocalDate(request.getProgramStartDate()))
                        .programFinishDate(customDateUtil.convertStringToLocalDate(request.getProgramFinishDate()))
                        .recruitStartDate(customDateUtil.convertStringToLocalDate(request.getRecruitStartDate()))
                        .recruitFinishDate(customDateUtil.convertStringToLocalDate(request.getRecruitFinishDate()))
                        .capacity(request.getCapacity())
                        .programPlace(request.getProgramPlace())
                    .build()
        );

        // [1-2] Program Entity map
        List<ProgramWeek> programWeeks = request.getProgramWeeks().stream().map(programWeek -> ProgramWeek.builder()
                .program(program)
                .content(programWeek.getContent())
                .registerDate(customDateUtil.convertStringToLocalDate(programWeek.getRegisterDate()))
                .build()).toList();

        // [1-3] ProgramWeeks 기본 정보 등록
        programWeekRepository.saveAll(programWeeks);

        // [1-4] ProgramCategory 기본 정보 등록
        List<ProgramCategory> categories = request.getProgramCategories().stream().map(category ->
                ProgramCategory.builder()
                        .parent(category.getParent())
                        .child(category.getChild())
                        .build()).toList();

        programCategoryRepository.saveAll(categories);
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


