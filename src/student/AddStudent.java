package student;

// 增加学生记录

import user.ConnectSql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddStudent extends JPanel implements ActionListener {
    JTextField Sno, Sname, Ssex, Sage, Sdept;
    JButton addButton, cancelButton;

    // 实现增加学生记录界面
    public AddStudent() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Exception:" + e);
        }

        Sno = new JTextField(12);
        Sname = new JTextField(12);
        Ssex = new JTextField(12);
        Sage = new JTextField(12);
        Sdept = new JTextField(12);
        addButton = new JButton("录入");
        addButton.addActionListener(this);
        cancelButton = new JButton("取消");
        cancelButton.addActionListener(this);
        Box box0 = Box.createHorizontalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createHorizontalBox();
        Box box6 = Box.createHorizontalBox();

        JLabel title = new JLabel("增加学生信息");
        title.setFont(new Font("宋体", Font.BOLD, 12));
        box0.add(title);
        box1.add(new JLabel("学号:"));
        box1.add(Sno);
        box2.add(new JLabel("姓名:"));
        box2.add(Sname);
        box3.add(new JLabel("性别:"));
        box3.add(Ssex);
        box4.add(new JLabel("年龄:"));
        box4.add(Sage);
        box5.add(new JLabel("系别:"));
        box5.add(Sdept);
        box6.add(addButton);
        box6.add(cancelButton);
        Box boxH = Box.createVerticalBox();
        boxH.add(box1);
        boxH.add(box2);
        boxH.add(box3);
        boxH.add(box4);
        boxH.add(box5);
        boxH.add(box6);
        boxH.add(Box.createVerticalGlue());
        JPanel messPanel1 = new JPanel();
        JPanel messPanel2 = new JPanel();
        messPanel1.add(box0);
        messPanel2.add(boxH);
        setLayout(new BorderLayout());
        JSplitPane splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messPanel1, messPanel2);// 分割
        splitV.setEnabled(false);
        add(splitV, BorderLayout.CENTER);
        validate();
    }

    // 实现增加学生记录事件响应
    public void actionPerformed(ActionEvent c) {
        Object obj = c.getSource();
        if (obj == addButton) {
            if (Sno.getText().equals("") || Sname.getText().equals("") || Ssex.getText().equals("") || Sage.getText().equals("")
                    || Sdept.getText().equals("")) { // 保证student表中每个字段都是非空
                JOptionPane.showMessageDialog(this, "请完善信息后再添加！");
            }
            // 执行SQL语句并返回结果集
            Statement stmt = null;
            ResultSet rs = null;
            String sql1, sql2;
            sql1 = "insert into student values('" + Sno.getText() + "','" + Sname.getText() + "','" + Ssex.getText() + "','"
                    + Sage.getText() + "','" + Sdept.getText() + "')";
            sql2 = "select * from student where Sno='" + Sno.getText() + "'";
            System.out.print(sql1 + "\n");
            System.out.print(sql2 + "\n");
            try {
                Connection dbConn = ConnectSql.CONN();
                stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                rs = stmt.executeQuery(sql2);
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "该学号已存在，无法添加!");
                } else {
                    stmt.executeUpdate(sql1);   // 添加记录
                    Sno.setText("");
                    Sname.setText("");
                    Ssex.setText("");
                    Sage.setText("");
                    Sdept.setText("");
                    JOptionPane.showMessageDialog(this, "添加完成!");
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                System.out.print("SQL Exception:" + e.getMessage());
            }
        }else{
            if(obj == cancelButton)
            {
                Sno.setText("");
                Sname.setText("");
                Ssex.setText("");
                Sage.setText("");
                Sdept.setText("");
            }
        }
    }


}
