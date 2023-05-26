package tuk.mentor.domain.program.service;


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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuk.mentor.domain.user.mentee.repository.MenteeRepository;
import tuk.mentor.domain.user.mentor.entity.Mentor;
import tuk.mentor.domain.user.mentor.repository.MentorRepository;
import tuk.mentor.domain.program.dto.request.ProgramParticipateRequest;
import tuk.mentor.domain.program.dto.request.ProgramRegisterRequest;
import tuk.mentor.domain.program.dto.response.ProgramDetailResponse;
import tuk.mentor.domain.program.dto.response.ProgramListResponse;
import tuk.mentor.domain.program.entity.Program;
import tuk.mentor.domain.program.entity.ProgramParticipation;
import tuk.mentor.domain.program.entity.ProgramWeek;
import tuk.mentor.domain.program.mapper.ProgramMapper;
import tuk.mentor.domain.program.repository.ProgramParticipationRepository;
import tuk.mentor.domain.program.repository.ProgramRepository;
import tuk.mentor.domain.program.repository.ProgramWeekRepository;
import tuk.mentor.login.LoginInfo;
import tuk.mentor.util.DateUtil;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final ProgramWeekRepository programWeekRepository;
    private final ProgramParticipationRepository programParticipationRepository;
    private final MentorRepository mentorRepository;
    private final MenteeRepository menteeRepository;
    private final ProgramMapper programMapper;
    private final DateUtil dateUtil;

    /*
     * 프로그램 등록
     * */
    @Transactional
    public void registerProgram(ProgramRegisterRequest request, HttpServletRequest httpServletRequest) {
        // [1] 프로그램 기본 정보 등록
        // [1-1] 세션에 등록된 로그인 정보 조회 및 멘토 조회
        HttpSession session = httpServletRequest.getSession();
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Mentor mentor = mentorRepository.findById(loginInfo.getUserID()).orElseThrow(RuntimeException::new);

        /*
         * 문제1. programMapper.toEntity(request, mentor, programWeeks)를 하면 program.id 가 null이 아님.
         * programRepository.save(program); 시 insert전에 select를 먼저 함. 이때 일치하는 program 정보가 있는 탓인지 insert를 안하는 문제가 있음.
         * 문제2. program_week 테이블의 program_id(fk)도 insert되지 않는 문제가 있다.
         *
         * 해결1 : select -> insert문제는 해결 못함
         * 해결2 : program_week 테이블의 program_id(fk)도 insert되지 않는 문제는 saveAll로 해결
         * */
        Program program = programRepository.save(
                Program.builder()
                    .mentor(mentor)
                    .subject(request.getSubject())
                        .detail(request.getDetail())
                        .programStartDate(dateUtil.convertStringToLocalDate(request.getProgramStartDate()))
                        .programFinishDate(dateUtil.convertStringToLocalDate(request.getProgramFinishDate()))
                        .recruitStartDate(dateUtil.convertStringToLocalDate(request.getRecruitStartDate()))
                        .recruitFinishDate(dateUtil.convertStringToLocalDate(request.getRecruitFinishDate()))
                        .capacity(request.getCapacity())
                        .programPlace(request.getProgramPlace())
                    .build()
        );

        // [1-2] Program Entity map
        List<ProgramWeek> programWeeks = request.getProgramWeeks().stream().map(programWeek -> ProgramWeek.builder()
                .program(program)
                .content(programWeek.getContent())
                .programWeekStartDate(dateUtil.convertStringToLocalDate(programWeek.getProgramWeekStartDate()))
                .programWeekFinishDate(dateUtil.convertStringToLocalDate(programWeek.getProgramWeekFinishDate()))
                .build()).collect(Collectors.toList());

        // [1-3] Program & ProgramWeeks 기본 정보 등록
        programWeekRepository.saveAll(programWeeks);

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
        programParticipationRepository.save(ProgramParticipation.builder()
                .program(programRepository.findById(request.getProgramId()).orElseThrow(EntityNotFoundException::new))
                .mentee(menteeRepository.findById(request.getMenteeId()).orElseThrow(EntityNotFoundException::new))
                .build());
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

            String mentorInfo = "멘토명: " + program.getMentor().getName() + "\n"
                        + "출신학교: "+ program.getMentor().getCollege() + "\n"
                        + "전공: " + program.getMentor().getMajor() + "\n"
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


