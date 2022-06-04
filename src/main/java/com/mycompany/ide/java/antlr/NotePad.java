/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ide.java.antlr;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
        
import javax.swing.text.Element;

/**
 * GEEKS FOR GEEKS
 * @author sebjaiovi
 */
public class NotePad extends JFrame implements ActionListener, WindowListener{
    //JTextPane jta = new JTextPane();
    private static JTextArea jta;
    JTextArea jtaColoreado = new JTextArea();
    private static JTextArea lines; //numeritos
    
    private static JLabel statusLabel;
    private String pathArchivo;
    File fnameContainer;
    
    public NotePad(){
        //Definicion de area de texto y numero de linea
        jta = new JTextArea();
        lines = new JTextArea("1");
        lines.setBackground(Color.LIGHT_GRAY);
        lines.setEditable(false);
        //Definimos font normal
        Font fnt=new Font("Arial",Font.PLAIN,15);
        Container con=getContentPane();

        //interfaz grafica PT1
        JMenuBar jmb=new JMenuBar();
        JMenu jmfile = new JMenu("Archivo");
        JMenu jmedit = new JMenu("Editar");
        JMenu jmide = new JMenu("IDE");
        JMenu jmhelp = new JMenu("Ayuda");

        con.setLayout(new BorderLayout());
        JScrollPane sbrText = new JScrollPane(jta);
        sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sbrText.setVisible(true);

        //definimos en general font
        jta.setFont(fnt);
        //DEL TEXT AREA, reemplazado por PANE
        //jta.setLineWrap(true);
        //jta.setWrapStyleWord(true);

        //donde el container recibe nuestra caja de texto especificada
        con.add(sbrText);

        //mas de la gui
        createMenuItem(jmfile,"Nuevo");
        createMenuItem(jmfile,"Abrir");
        createMenuItem(jmfile,"Guardar");
        jmfile.addSeparator();
        createMenuItem(jmfile,"Salir");

        createMenuItem(jmedit,"Cortar");
        createMenuItem(jmedit,"Copiar");
        createMenuItem(jmedit,"Pegar");

        createMenuItem(jmide, "Correr");

        createMenuItem(jmhelp,"Creditos");

        jmb.add(jmfile);
        jmb.add(jmedit);

        jmb.add(jmide);

        jmb.add(jmhelp);

        setJMenuBar(jmb);
        setIconImage(Toolkit.getDefaultToolkit().getImage("notepad.gif"));
        addWindowListener(this);
        setSize(500,500);
        setTitle("SinTitulo.txt - MatricesIDE");

        //adicional para reportar DEBUGGING del compilado

        //String notifica = "Hola"; YA EN CABECERA
        // create the status bar panel and shove it down the bottom of the frame https://stackoverflow.com/questions/3035880/how-can-i-create-a-bar-in-the-bottom-of-a-java-app-like-a-status-bar
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        con.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(con.getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        //JLabel statusLabel = new JLabel(""+notifica); //varible a modificar
        statusLabel= new JLabel();
        statusLabel.setText("Listo :)");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);	
                
                
      //  Code to implement line numbers inside the JTextArea
      jta.getDocument().addDocumentListener(new DocumentListener() {
         public String getText() {
            int caretPosition = jta.getDocument().getLength();
            Element root = jta.getDocument().getDefaultRootElement();
            String text = "1" + System.getProperty("line.separator");
               for(int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
                  text += i + System.getProperty("line.separator");
               }
            return text;
         }
         @Override
         public void changedUpdate(DocumentEvent de) {
            lines.setText(getText());
         }
         @Override
         public void insertUpdate(DocumentEvent de) {
            lines.setText(getText());
         }
         @Override
         public void removeUpdate(DocumentEvent de) {
            lines.setText(getText());
         }
      });
      
                //con.setRowHeaderView(lines);
                
                //lines.setBounds(16, 32, 10, 20); // TODO: Unir barra numeros linea con el editor de texto 
                //add(lines);
		//setVisible(true);
                sbrText.getViewport().add(jta);
                sbrText.setRowHeaderView(lines);
                add(sbrText);
                setSize(400, 275);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                setVisible(true);
                
                //Generar Terminal : codigo trabaj
	
	}

	public void createMenuItem(JMenu jm,String txt){ //snippet que crea los accionables de cada menu
		JMenuItem jmi=new JMenuItem(txt);
		jmi.addActionListener(this);
		jm.add(jmi);
	}
	
	public void actionPerformed(ActionEvent e){	
		JFileChooser jfc=new JFileChooser();
		
		if(e.getActionCommand().equals("Nuevo")){ 
			//new
			this.setTitle("SinTitulo.txt - MatricesIDE");
			jta.setText("");
			fnameContainer=null;
		}else if(e.getActionCommand().equals("Abrir")){
			//open
			int ret=jfc.showDialog(null,"Abrir");
			
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				try{
					File fyl=jfc.getSelectedFile();
					OpenFile(fyl.getAbsolutePath());
					this.setTitle(fyl.getName()+ " - MatricesIDE");
					fnameContainer=fyl;
				}catch(IOException ers){}
			}
			
		}else if(e.getActionCommand().equals("Guardar")){
			//save
			if(fnameContainer != null){
				jfc.setCurrentDirectory(fnameContainer);		
				jfc.setSelectedFile(fnameContainer);
			}
			else {
				//jfc.setCurrentDirectory(new File("."));
				jfc.setSelectedFile(new File("SinTitulo.txt"));
			}
			
			int ret=jfc.showSaveDialog(null);
				
			if(ret == JFileChooser.APPROVE_OPTION){
				try{
					
					File fyl=jfc.getSelectedFile();
					SaveFile(fyl.getAbsolutePath());
					this.setTitle(fyl.getName()+ " - MatricesIDE");
					fnameContainer=fyl;
					
				}catch(Exception ers2){}
			}
			
		}else if(e.getActionCommand().equals("Salir")){
			Exiting();
		}else if(e.getActionCommand().equals("Copiar")){
			jta.copy();
		}else if(e.getActionCommand().equals("Pegar")){
			jta.paste();
		}else if(e.getActionCommand().equals("Creditos")){ 
			JOptionPane.showMessageDialog(this,"MatricesIDE - Programa creado para Metodos Computacionales 4SEM - Lunes 6 junio 2022","MatricesIDE",JOptionPane.INFORMATION_MESSAGE);
		}else if(e.getActionCommand().equals("Cortar")){
			jta.cut();
		}else if(e.getActionCommand().equals("Correr")){
                        //String notifica = Compilar();
                        statusLabel.setText( Compilar() ); //Compilar devuelve String marcando linea error
                }
	}
	
	public void OpenFile(String fname) throws IOException {	
		//open file code here
		BufferedReader d=new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
		String l;
		//clear the textbox
		jta.setText("");
	
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
			
		while((l=d.readLine())!= null) {
			jta.setText(jta.getText()+l+"\r\n");
		}
		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		d.close();
	}
	
	public void SaveFile(String fname) throws IOException {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		DataOutputStream o=new DataOutputStream(new FileOutputStream(fname));
		o.writeBytes(jta.getText());
		o.close();		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void windowDeactivated(WindowEvent e){}
	public void windowActivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	
	public void windowClosing(WindowEvent e) {
		Exiting();
	}
	
	public void windowOpened(WindowEvent e){}
	
	public void Exiting() {
		System.exit(0);
	}
        
        //FUNCIONALIDADES
        public String Compilar(){
            boolean error;
            //
            // mete todo el proceso de compilado aqui
            //
            error = true;
            
            if(error){
                JOptionPane.showMessageDialog(this,"Hubo error para compilar en x linea","MatricesIDE error",JOptionPane.INFORMATION_MESSAGE);
                return "Linea X" + ": Error del tipo " + "bla bla";
            }else
                return "Compilado correcto c:";
        }
}
