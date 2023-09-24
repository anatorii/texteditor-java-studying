import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainWindow extends JFrame {
    private static int width = 800;
    private static int height = 600;
    private JPanel panel;
    private JButton openButton;
    private JButton saveButton;
    private JButton saveAsButton;
    private JLabel nameLabel;
    private JTextArea textArea1;
    String path = "";

    public MainWindow() {
        super("Редактор текста");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(MainWindow.width, MainWindow.height);
        this.setLocation(d.width / 2 - MainWindow.width / 2, d.height / 2 - MainWindow.height / 2);
        this.getContentPane().add(panel);
        textArea1.grabFocus();

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(MainWindow.this, "Выбор файла", FileDialog.LOAD);
                fd.setDirectory(System.getProperty("user.home"));
                fd.setFilenameFilter(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".txt");
                    }
                });
                fd.setVisible(true);
                String filename = fd.getFile();

                if (filename == null) {
                    System.out.println("You cancelled the choice");
                } else {
                    System.out.println("You chose " + fd.getDirectory() + filename);
                    FileReader fr;
                    try {
                        path = fd.getDirectory() + filename;
                        fr = new FileReader(new File(path));
                        BufferedReader br = new BufferedReader(fr);
                        textArea1.setText("");
                        if (br.ready()) {
                            String line = br.readLine();
                            while (line != null) {
                                textArea1.append(line + "\n");
                                line = br.readLine();
                            }
                        }
                        nameLabel.setText("Открыт файл: " + path);
                        textArea1.grabFocus();
                    } catch (IOException ex) {
                        path = "";
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(path);
                try {
                    if (path.length() != 0) {
                        FileWriter fw = new FileWriter(new File(path));
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(textArea1.getText());
                        bw.close();
                        fw.close();
                    }
                    textArea1.grabFocus();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        saveAsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(MainWindow.this, "Выбор файла", FileDialog.SAVE);
                fd.setFilenameFilter(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".txt");
                    }
                });
                fd.setDirectory(System.getProperty("user.home"));
                fd.setVisible(true);
                String filename = fd.getFile();

                if (filename == null) {
                    System.out.println("You cancelled the choice");
                } else {
                    System.out.println("You chose " + fd.getDirectory() + filename);
                    try {
                        File fileToSave = new File(fd.getDirectory() + filename);
                        FileWriter fw = new FileWriter(fileToSave);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(textArea1.getText());
                        bw.close();
                        fw.close();
                        path = fileToSave.getAbsolutePath();
                        nameLabel.setText("Открыт файл: " + path);
                        textArea1.grabFocus();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

}
