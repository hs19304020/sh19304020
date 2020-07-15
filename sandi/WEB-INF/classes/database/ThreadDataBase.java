//database��thread_table�ɐڑ����鏈��������
package database;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import info.ThreadListProfile;
import info.Conversion;

public class ThreadDataBase{
  // public static void main(String[] args){
  //   CreateThread(args[0],args[1],args[2]);
  // }

  //thread_table��insert����
  public static String CreateThread(String name, String description, String theme){
    //������
    String id=null; String date=null;  String count="0";

    try{
      Class.forName("oracle.jdbc.driver.OracleDriver");

      //Oracle�ɐڑ�����
      Connection cn=
      DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
      "sandi","pass");
      System.out.println("�ڑ�����");

      //date�ɍ����̓��t����
      Calendar cal=Calendar.getInstance();
      Date d=cal.getTime();
      SimpleDateFormat today=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
      date=today.format(d);

      //thread_id�ɐV����id�������
      String getid="SELECT MAX(thread_id)+1 FROM thread_table";
      Statement stt1=cn.createStatement();
      ResultSet rst1=stt1.executeQuery(getid);
      while(rst1.next()){
        id = rst1.getString(1);
      }
      stt1.close();

      if(id==null)id="1";

      //�m�F�p
      System.out.println("id="+id+",name="+name+",theme="+theme+",date="+date+",description="+description+",count="+count);

      //insert��
      String insert="insert into THREAD_TABLE values('"+id+"','"+name+"','"+theme+"',sysdate,'"+description+"',"+count+")";
      //Statement�C���^�[�t�F�C�X����������N���X���C���X�^���X������
      Statement st=cn.createStatement();
      //insert�������s��
      //ResultSet�C���^�[�t�F�C�X�����������N���X�̃C���X�^���X���Ԃ�
      ResultSet rs=st.executeQuery(insert);
      System.out.println("INSERT�����I");

      //Oracle����ؒf����
      cn.close();
      System.out.println("�ؒf����");

    }catch(ClassNotFoundException e){
      e.printStackTrace();
      System.out.println("�N���X���Ȃ��݂����B");
    }catch(SQLException e){
      e.printStackTrace();
      System.out.println("SQL�֘A�̗�O�݂����B");
    }catch(Exception e){
      e.printStackTrace();
    }
    //ThreadServlet�ɖ߂�
    return id;
  }

  //thread_table����select����
  public static List<ThreadListProfile> getFamousThread(String theme){
    Conversion con=new Conversion();
    //ThreadServlet�ɕԂ����߂̃��X�g
		List<ThreadListProfile> threadList = new ArrayList<ThreadListProfile>();
    String id=null;
    //String theme=null;
    String name=null; String description=null; Integer count=null; String date=null;
    try{
      Class.forName("oracle.jdbc.driver.OracleDriver");

      //Oracle�ɐڑ�����
      Connection cn=
      DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
      "sandi","pass");
      System.out.println("�ڑ�����");

      //select���@�J�e�S���������̏ꍇ���ׂĕ\���A����ȊO�̓e�[�}���Ƃɕ\��
      String getthred;
      if(theme.equals("1")){
        // System.out.println("if:"+theme);
        getthred="SELECT thread_id, thread_name, thread_theme, to_char(thread_date,'YYYY/MM/DD HH24:MI:SS DAY'), thread_description, thread_count FROM thread_table ORDER BY thread_count desc";
      }else{
        // System.out.println("if:"+theme);
        getthred="SELECT thread_id, thread_name, thread_theme, to_char(thread_date,'YYYY/MM/DD HH24:MI:SS DAY'), thread_description, thread_count FROM thread_table WHERE thread_theme='"+theme+"' ORDER BY thread_count desc";
      }
      //Statement�C���^�[�t�F�C�X����������N���X���C���X�^���X������
      Statement stt2=cn.createStatement();
      //select�������s��
      //ResultSet�C���^�[�t�F�C�X�����������N���X�̃C���X�^���X���Ԃ�
      ResultSet rst2=stt2.executeQuery(getthred);

      while(rst2.next()){
				ThreadListProfile prof = new ThreadListProfile();

        id=rst2.getString(1);
        name=rst2.getString(2);
        theme=rst2.getString(3);
        date=rst2.getString(4);
        description=rst2.getString(5);
        count=Integer.parseInt(rst2.getString(6));

        if(theme.equals("1"))
          theme="����";
        if(theme.equals("2"))
          theme="�G�k";
        if(theme.equals("3"))
          theme="���k";
        if(theme.equals("4"))
          theme="�";

        //bean�t�@�C���Œl��set���Alist��add����
        prof.setId(id);
        prof.setName(name);
        prof.setDescription(description);
        prof.setTheme(theme);
        prof.setCount(count);
        prof.setDate(date);
        threadList.add(prof);
      }
      //�m�F�p
      System.out.println("id="+id+",name:"+name+",theme:"+theme+",date:"+date+",description:"+description+",count:"+count);

      cn.close();
			System.out.println("�ؒf����");

    }catch(ClassNotFoundException e){
      e.printStackTrace();
      System.out.println("�N���X���Ȃ��݂����B");
    }catch(SQLException e){
      e.printStackTrace();
      System.out.println("SQL�֘A�̗�O�݂����B");
    }catch(Exception e){
      e.printStackTrace();
    }
    return threadList;
  }

  public static List<ThreadListProfile> getNewThread(String theme){
    Conversion con=new Conversion();
    //ThreadServlet�ɕԂ����߂̃��X�g
    List<ThreadListProfile> threadList = new ArrayList<ThreadListProfile>();
    String id=null;
    //String theme=null;
    String name=null; String description=null; Integer count=null; String date=null;
    try{
      Class.forName("oracle.jdbc.driver.OracleDriver");

      //Oracle�ɐڑ�����
      Connection cn=
      DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
      "sandi","pass");
      System.out.println("�ڑ�����");

      //select���@�J�e�S���������̏ꍇ���ׂĕ\���A����ȊO�̓e�[�}���Ƃɕ\��
      String getthred;
      if(theme.equals("1")){
        // System.out.println("if:"+theme);
        getthred="SELECT thread_id, thread_name, thread_theme, to_char(thread_date,'YYYY/MM/DD HH24:MI:SS DAY'), thread_description, thread_count FROM thread_table ORDER BY thread_date desc";
      }else{
        // System.out.println("if:"+theme);
        getthred="SELECT thread_id, thread_name, thread_theme, to_char(thread_date,'YYYY/MM/DD HH24:MI:SS DAY'), thread_description, thread_count FROM thread_table WHERE thread_theme='"+theme+"' ORDER BY thread_date desc";
      }
      //Statement�C���^�[�t�F�C�X����������N���X���C���X�^���X������
      Statement stt2=cn.createStatement();
      //select�������s��
      //ResultSet�C���^�[�t�F�C�X�����������N���X�̃C���X�^���X���Ԃ�
      ResultSet rst2=stt2.executeQuery(getthred);

      while(rst2.next()){
        ThreadListProfile prof = new ThreadListProfile();

        id=rst2.getString(1);
        name=rst2.getString(2);
        theme=rst2.getString(3);
        date=rst2.getString(4);
        description=rst2.getString(5);
        count=Integer.parseInt(rst2.getString(6));

        if(theme.equals("1"))
          theme="����";
        if(theme.equals("2"))
          theme="�G�k";
        if(theme.equals("3"))
          theme="���k";
        if(theme.equals("4"))
          theme="�";

        //bean�t�@�C���Œl��set���Alist��add����
        prof.setId(id);
        prof.setName(name);
        prof.setDescription(description);
        prof.setTheme(theme);
        prof.setCount(count);
        prof.setDate(date);
        threadList.add(prof);
      }
      //�m�F�p
      System.out.println("id="+id+",name:"+name+",theme:"+theme+",date:"+date+",description:"+description+",count:"+count);

      cn.close();
      System.out.println("�ؒf����");

    }catch(ClassNotFoundException e){
      e.printStackTrace();
      System.out.println("�N���X���Ȃ��݂����B");
    }catch(SQLException e){
      e.printStackTrace();
      System.out.println("SQL�֘A�̗�O�݂����B");
    }catch(Exception e){
      e.printStackTrace();
    }
    return threadList;
  }

  public static List<ThreadListProfile> getThreadDescription(String id){
    Conversion con=new Conversion();
    //ThreadServlet�ɕԂ����߂̃��X�g
    List<ThreadListProfile> threadList = new ArrayList<ThreadListProfile>();
    //String theme=null;
    String name=null; String theme=null; String description=null; Integer count=null; String date=null;
    try{
      Class.forName("oracle.jdbc.driver.OracleDriver");

      //Oracle�ɐڑ�����
      Connection cn=
      DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
      "sandi","pass");
      System.out.println("�ڑ�����");

      //select���@�J�e�S���������̏ꍇ���ׂĕ\���A����ȊO�̓e�[�}���Ƃɕ\��
      String getthred="SELECT thread_id, thread_name, thread_theme, to_char(thread_date), thread_description, thread_count FROM thread_table WHERE thread_id="+id;

      //Statement�C���^�[�t�F�C�X����������N���X���C���X�^���X������
      Statement stt2=cn.createStatement();
      //select�������s��
      //ResultSet�C���^�[�t�F�C�X�����������N���X�̃C���X�^���X���Ԃ�
      ResultSet rst2=stt2.executeQuery(getthred);

      while(rst2.next()){
        ThreadListProfile prof = new ThreadListProfile();

        id=rst2.getString(1);
        name=rst2.getString(2);
        theme=rst2.getString(3);
        date=rst2.getString(4);
        description=rst2.getString(5);
        count=Integer.parseInt(rst2.getString(6));

        if(theme.equals("1"))
          theme="����";
        if(theme.equals("2"))
          theme="�G�k";
        if(theme.equals("3"))
          theme="���k";
        if(theme.equals("4"))
          theme="�";

        //bean�t�@�C���Œl��set���Alist��add����
        prof.setId(id);
        prof.setName(name);
        prof.setDescription(description);
        prof.setTheme(theme);
        prof.setCount(count);
        prof.setDate(date);
        threadList.add(prof);
      }
      //�m�F�p
      System.out.println("id="+id+",name:"+name+",theme:"+theme+",date:"+date+",description:"+description+",count:"+count);

      cn.close();
      System.out.println("�ؒf����");

    }catch(ClassNotFoundException e){
      e.printStackTrace();
      System.out.println("�N���X���Ȃ��݂����B");
    }catch(SQLException e){
      e.printStackTrace();
      System.out.println("SQL�֘A�̗�O�݂����B");
    }catch(Exception e){
      e.printStackTrace();
    }
    return threadList;
  }

  //������update���Ȃ��\��turead_table�\
  //���thread_count �^��number 8
  public static Integer updateThread(String id, Integer count){

    try{
      Class.forName("oracle.jdbc.driver.OracleDriver");

      //Oracle�ɐڑ�����
      Connection cn=
      DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
      "sandi","pass");
      System.out.println("�ڑ�����");

      String selectcount="SELECT thread_count FROM thread_table WHERE thread_id="+id;
      Statement stt1=cn.createStatement();
      ResultSet rst1=stt1.executeQuery(selectcount);

      while(rst1.next()){
        count = Integer.parseInt(rst1.getString(1));
      }
      stt1.close();

      if(count==null)id="1";

      //update���@�J�e�S���������̏ꍇ���ׂĕ\���A����ȊO�̓e�[�}���Ƃɕ\��
      count++;
      String update="UPDATE thread_table SET thread_count="+count+"WHERE thread_id="+id;


      //Statement�C���^�[�t�F�C�X����������N���X���C���X�^���X������
      Statement stt2=cn.createStatement();
      //update�������s��
      //ResultSet�C���^�[�t�F�C�X�����������N���X�̃C���X�^���X���Ԃ�
      ResultSet rst2=stt2.executeQuery(update);


      cn.close();
      System.out.println("�ؒf����");

    }catch(ClassNotFoundException e){
      e.printStackTrace();
      System.out.println("�N���X���Ȃ��݂����B");
    }catch(SQLException e){
      e.printStackTrace();
      System.out.println("SQL�֘A�̗�O�݂����B");
    }catch(Exception e){
      e.printStackTrace();
    }
    return count;
  }
}
