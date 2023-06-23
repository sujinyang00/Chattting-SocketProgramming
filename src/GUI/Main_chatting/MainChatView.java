package GUI.Main_chatting;

import GUI.Log_in_GUI.ClientConnect;
import Server.Room;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Vector;

public class MainChatView extends JFrame{
    private String id;

    private JLabel RoomName;
    private JButton btnInvite;
    private JButton btnSend;
    public JPanel Chat;
    public JTextField chattingBox;
    public JTextArea chattingArea;
    public JScrollPane scoll;

    public ClientConnect client;
    private String room_name;
    public Vector<Room> myRoom; //클라들이 들어가있는 채팅방 객체들

    public MainChatView(String room_name, ClientConnect client){
        this.client = client;
        this.room_name = room_name;
        RoomName.setText(room_name);



        btnInvite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(id);
                ChatInviteView chatInviteView = new ChatInviteView(client, room_name);

            }
        });

        //전송버튼
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                msgSummit();
                chattingBox.requestFocus();
            }
        });

        //채팅 입력하는 chattingBox (JTextField)
        chattingBox.addKeyListener(new KeyAdapter() { //채팅입력하고 엔터
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    msgSummit();
                }
            }
        });

    }


    public void msgSummit(){ //String str = chattingBox에 입력한 내용
        String str = chattingBox.getText();

        //여기서 appendTest()부르면 각자 채팅방에서만 + 오류x

        if(!str.equals("")){
            if(str.length() > 200){
                JOptionPane.showMessageDialog(null, "200자 이하로 입력해주세요.");
                return;
            }
            try {
                client.getDos().writeUTF("300|"+room_name+"|"+str);

                chattingBox.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





    public void showmainchatview(String room_name) {
        JFrame frame = new JFrame("메인 채팅");
        frame.setContentPane(Chat);//?
        frame.setLocation(900, 280);
        frame.pack();
        frame.setSize(450, 550);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                try{
                    client.getDos().writeUTF("250|" + room_name);
                    for(int i = 0; i < client.myRoom.size(); i++){
                        if(client.myRoom.get(i).title.equals(room_name)){
                            client.myRoom.remove(i);
                        }
                    }

                }catch(IOException e1){
                    e1.printStackTrace();
                }

            }
        });

    }



}
