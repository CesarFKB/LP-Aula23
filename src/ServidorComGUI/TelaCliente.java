package ServidorComGUI;
import javax.swing.*;

import ServidorSemGUI.ClientBatepapo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TelaCliente extends JFrame implements ActionListener {
    private JButton btnEnviar, btnLimpar, btnSair;
    private JLabel lblStatus, lblRecebido;
    private JTextField txtMensagem;
    private ClientBatepapo client;

    public TelaCliente() {
        iniciarElementos();
    }

    private void iniciarElementos() {

        btnEnviar = new JButton("Enviar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        btnEnviar.addActionListener(this);
        btnLimpar.addActionListener(this);
        btnSair.addActionListener(this);

        lblStatus = new JLabel("Status: ");
        lblRecebido = new JLabel("Ultima Mensagem: ");

        txtMensagem = new JTextField(20);

        definirLayout();
    }

    private void definirLayout() {
        Container cont = getContentPane();
        cont.setLayout(new GridLayout(3, 1)); 

        JPanel painelBotoes = new JPanel(new GridLayout(1, 3));

        painelBotoes.add(btnEnviar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        cont.add(txtMensagem);
        cont.add(painelBotoes);

        JPanel painelLabels = new JPanel(new GridLayout(2, 1));
        painelLabels.add(lblStatus);
        painelLabels.add(lblRecebido);

        cont.add(painelLabels);

        setVisible(true);
        setMinimumSize(new Dimension(400, 200));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void iniciaCliente() {
        try {
            client = new ClientBatepapo();
            client.start();
        } catch (IOException e) {
            System.out.println("Erro ao iniciar cliente: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        TelaCliente tc = new TelaCliente();
        tc.iniciaCliente();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
