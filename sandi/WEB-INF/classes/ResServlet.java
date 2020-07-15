//TimeLine.jsp������͂��ꂽ����ResDBServlet��ʂ��Ares_table�\�ɕۊǂ���
//ThreadDBServlet��ʂ��Ares_table�\����f�[�^���擾���ATimeLine.jsp�ɏo�͂���
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.List;
import database.ResDataBase;
import info.ResListProfile;
import database.ThreadDataBase;
import info.ThreadListProfile;
import info.Conversion;

public class ResServlet extends HttpServlet{
  Conversion con=new Conversion();
  public void init(ServletConfig config) throws ServletException{
		super.init(config);
		Integer count = 0;
		ServletContext application = config.getServletContext();
		application.setAttribute("count", count);
		System.out.println("ini()�����s����܂���");
	}

  //insert�p
  public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
    ResDataBase resdata = new ResDataBase();
    ThreadDataBase thredata=new ThreadDataBase();

    //request���Ŏg���Ă镶���R�[�h�̐ݒ�
    req.setCharacterEncoding("UTF-8");

    //thread_id,name,text���󂯎�肽��
    //thread_name��thread����\�����邽��
    String thread_id=req.getParameter("thread_id");
    String name=req.getParameter("name");
    String text=req.getParameter("text");
    String thread_name = con.conversionText(req.getParameter("thread_name"));

    //ResDataBase��thread_id,name,text�𑗂�
    String id=resdata.createRes(thread_id,name,text);

    //list�C���^�[�t�F�[�X��ResDataBase���o�R����res_table�\�̒l��������
    List<ResListProfile> rlist = resdata.getRes(thread_id);
    List<ThreadListProfile> tlist = thredata.getThreadDescription(thread_id);

    //res_table�\��������list�ƁAthread�̏���request��set
    req.setAttribute("thread_id",thread_id);
    req.setAttribute("thread_name",thread_name);
    req.setAttribute("rusers",rlist);
    req.setAttribute("tusers",tlist);

    //�����̎w��ijsp��action�Ɠ����j
    RequestDispatcher dis=req.getRequestDispatcher("timeline");
    dis.forward(req,res);
  }

  //select�p
  public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{
    ResDataBase resdata=new ResDataBase();
    ThreadDataBase thredata=new ThreadDataBase();
    Conversion con=new Conversion();

    ServletContext application = this.getServletContext();
    Integer count = (Integer)application.getAttribute("count");
    count++;
    application.setAttribute("count", count);

    //request���Ŏg���Ă镶���R�[�h�̐ݒ�
    req.setCharacterEncoding("UTF-8");

    //url�p�����[�^��thread_id,name���󂯎�肽��
    //thread_name��thread����\�����邽��
    String thread_id = req.getParameter("id");
    String thread_name = con.conversionText(req.getParameter("name"));
    Integer thread_count = Integer.parseInt(req.getParameter("count"));

    System.out.println("thread_count:"+thread_count);

    //System.out.println(count);
    //list�C���^�[�t�F�[�X��ResDataBase���o�R����res_table�\�̒l��������
    List<ResListProfile> rlist = resdata.getRes(thread_id);
    List<ThreadListProfile> tlist = thredata.getThreadDescription(thread_id);
    Integer nandemoii = thredata.updateThread(thread_id, thread_count);

    req.setAttribute("thread_id",thread_id);
    req.setAttribute("thread_name",thread_name);
    req.setAttribute("rusers",rlist);
    req.setAttribute("tusers",tlist);

    //res_table�\��������list�ƁAthread�̏���request��set
    RequestDispatcher dis= req.getRequestDispatcher("timeline");

    dis.forward(req,res);
  }
}
