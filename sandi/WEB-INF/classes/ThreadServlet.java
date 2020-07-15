//CheckThread.jsp������͂��ꂽ����ThreadDBServlet��ʂ��Athread_table�\�ɕۊǂ���
//ThreadDBServlet��ʂ��Athread_table�\����f�[�^���擾���Atop.jsp�ɏo�͂���
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import database.ThreadDataBase;
import info.ThreadListProfile;
import info.Conversion;

public class ThreadServlet extends HttpServlet{
  //insert�p
  public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
    ThreadDataBase thre = new ThreadDataBase();
    Conversion con=new Conversion();
    //request���Ŏg���Ă镶���R�[�h�̐ݒ�
    req.setCharacterEncoding("UTF-8");

    //title,description,theme���󂯎��
    String title=con.conversionText(req.getParameter("title"));
    String description=con.conversionText(req.getParameter("description"));
    String theme=req.getParameter("theme");

    //database��insert����ɂ������Đ����ɕϊ�����
    if(theme.equals("�G�k"))
      theme="2";
    if(theme.equals("���k"))
      theme="3";
    if(theme.equals("�"))
      theme="4";

   //ThreadDatabase��title,description,theme�𑗂�B
    String id = thre.CreateThread(title,description,theme);

    //�����̎w��ijsp��action�Ɠ����j
    RequestDispatcher dis=req.getRequestDispatcher("completionthread");
    //���ۂɑ���
    dis.forward(req,res);
}

  //select�p
  public void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException,ServletException{

   //request���Ŏg���Ă镶���R�[�h�̐ݒ�
    req.setCharacterEncoding("UTF-8");

    //title,description,theme���󂯎��
    String id = req.getParameter("id");
    String theme = req.getParameter("theme");

    //list�C���^�[�t�F�[�X��ThreadDataBase���o�R����thread_table�\�̒l��������
    List<ThreadListProfile> famousList = ThreadDataBase.getFamousThread(theme);
    List<ThreadListProfile> newList = ThreadDataBase.getNewThread(theme);

    //res_table�\��������list�ƁAthread_id��request��set
    req.setAttribute("id",id);
    req.setAttribute("famousUsers",famousList);
    req.setAttribute("newUsers",newList);

    //�����̎w��ijsp��action�Ɠ����j
    RequestDispatcher dis= req.getRequestDispatcher("top");
    //���ۂɑ���
    dis.forward(req,res);
  }
}
