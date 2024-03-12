package interfaceswing;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;


import interfaceswing.ClientSwing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;
public class ClientSwing {

	private JFrame frame;
	private JTextField nomt;
	private JTable table;
	private JTextField tauxjournalier;
	private JTextField nbjrs;
	DefaultTableModel model;
	
	private static Scanner in = new Scanner(System.in);
	private JTextField id;
	
	private String nom;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientSwing window = new ClientSwing();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public ClientSwing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 590, 507);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		panel.setBounds(10, 0, 574, 468);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nom :");
		lblNewLabel.setBounds(10, 45, 45, 22);
		panel.add(lblNewLabel);
		
		JLabel lblTauxjournalier = new JLabel("Nb_jrs :");
		lblTauxjournalier.setBounds(10, 78, 51, 22);
		panel.add(lblTauxjournalier);
		
		nomt = new JTextField();
		
		nomt.setColumns(10);
		nomt.setBounds(68, 45, 107, 18);
		panel.add(nomt);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(7, 198, 554, 247);
		panel.add(scrollPane);
		
		
		//CONTENU TABLEAU 
		table = new JTable();
		table.setBackground(UIManager.getColor("InternalFrame.activeTitleGradient"));
		model = new DefaultTableModel();
		Object[] column = {"NOM", "NB_JRS", "TAUX_JRS", "PRESTATION", "ACTION"};
		Object[] row = new Object[5];
		//aona n fomba ammoana ny valinio calcul io eo amnio colonne io 
		row [3]= "Nbjrs * tauxJournalier";
		//aona manova anty ho button fa tsy texte tsotra otranio!!!
		row [4]= "DELETE  UPDATE";
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("Taux_jrs :");
		lblNewLabel_1.setBounds(10, 111, 61, 18);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel(" Histogramme :");
		lblNewLabel_3.setBounds(241, 11, 86, 18);
		panel.add(lblNewLabel_3);
		
		tauxjournalier = new JTextField();
		tauxjournalier.setColumns(10);
		tauxjournalier.setBounds(68, 111, 107, 18);
		panel.add(tauxjournalier);
		
		nbjrs = new JTextField();
		nbjrs.setColumns(10);
		nbjrs.setBounds(68, 80, 107, 18);
		panel.add(nbjrs);
		
		
		
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 ClientSwing http = new ClientSwing();
				 System.out.println("Test Http postRequest");
			     try {
					http.sendPost(nomt.getText(),nbjrs.getText(), tauxjournalier.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAjouter.setBounds(7, 164, 89, 23);
		panel.add(btnAjouter);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 ClientSwing http = new ClientSwing();
				 System.out.println("Test Http postRequest");
			     try {
					http.sendDelete(id.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnDelete.setBounds(420, 45, 89, 23);
		panel.add(btnDelete);
		
		JLabel lblId = new JLabel("id");
		lblId.setBounds(277, 45, 45, 22);
		panel.add(lblId);
		
		id = new JTextField();
		id.setColumns(10);
		id.setBounds(299, 47, 107, 18);
		panel.add(id);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 ClientSwing http = new ClientSwing();
				 System.out.println("Test Http postRequest");
			     try {
					http.sendEdit(id.getText(),nomt.getText(),nbjrs.getText(), tauxjournalier.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(122, 164, 89, 23);
		panel.add(btnUpdate);
		
		JButton btnLire = new JButton("Lire");
		btnLire.addActionListener(new ActionListener() {
			private String nom;
			public void actionPerformed(ActionEvent e) {
				 ClientSwing http = new ClientSwing();
				 System.out.println("Test Http postRequest");
			     try {
			    	//String resultat = http.sendGet();
			    	

			    	String responseString = sendGet(); 

			    	ObjectMapper mapper = new ObjectMapper();
			    	//String textValue = mapper.readValue(responseString, String.class);
			    	//System.out.println("Plain text value from JSON: " + textValue);
			    	

			    	
			    	String json = 
			                "{"
			                    + "'title': 'Computing and Information systems',"
			                    + "'id' : 1,"
			                    + "'children' : 'true',"
			                    + "'groups' : [{"
			                        + "'title' : 'Level one CIS',"
			                        + "'id' : 2,"
			                        + "'children' : 'true',"
			                        + "'groups' : [{"
			                            + "'title' : 'Intro To Computing and Internet',"
			                            + "'id' : 3,"
			                            + "'children': 'false',"
			                            + "'groups':[]"
			                        + "}]" 
			                    + "}]"
			                + "}";

			            // Now do the magic.
			            Data data = new Gson().fromJson(json, Data.class);

			            // Show it.
			            System.out.println(data);
			    	
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		btnLire.setBounds(271, 164, 89, 23);
		panel.add(btnLire);
		
		
		
	}
	
	 private String sendGet() throws Exception{
			
		 String url = "http://localhost/server%20api/medecin/read.php";
		 URL obj = new URL(url);
		 HttpURLConnection con = (HttpURLConnection)obj.openConnection();
		 con.setRequestMethod("GET");
		 con.setRequestProperty("Accept-Charset", "UTF-8");
		 System.out.println("response code: " + con.getResponseCode());
		 System.out.println("response message: " + con.getResponseMessage());
		 
		 BufferedReader in = new BufferedReader(
				 new InputStreamReader(con.getInputStream()));
				 String line;
				 StringBuffer response = new StringBuffer();
				 while ((line = in.readLine()) != null) {
				 response.append(line);
				 }
				 in.close();
				 return response.toString();
	 }
    private void sendPost(String Nom , String nb , String taux) throws Exception {
        in = new Scanner(System.in);
        String nom = Nom;
        String nbjrs = nb;
        String tauxjournalier = taux;
        
       

        // Utilisez des guillemets doubles pour les noms de clés et les valeurs
        String jsonString = "{\"nom\":\"" + URLEncoder.encode(nom, "UTF-8") + "\",\"nbjrs\":\"" + URLEncoder.encode(nbjrs, "UTF-8") + "\",\"tauxjournalier\":\"" + URLEncoder.encode(tauxjournalier, "UTF-8") + "\"}";

     
        
        String urlLink = "http://localhost/server%20api/medecin/create.php";
        URL url = new URL(urlLink);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Paramètres de la requête POST
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Écriture des données JSON dans le corps de la requête
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(jsonString.getBytes("UTF-8"));
        }

        // Lecture de la réponse
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();
            System.out.println("Response: " + response.toString());
        } else {
            System.out.println("POST error");
        }
    }
    
    
    //DELETE
    private void sendDelete(String Id) throws Exception {
        // Supposons que vous ayez une variable représentant l'identifiant à supprimer
        String id = Id;
       
        // Construire la chaîne de l'URL avec l'identifiant
        String urlLink = "http://localhost/server%20api/medecin/delete.php?id=" + URLEncoder.encode(String.valueOf(id), "UTF-8");
        URL url = new URL(urlLink);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Paramètres de la requête DELETE
        conn.setRequestMethod("DELETE");
        conn.setDoOutput(true);

        // Lecture de la réponse
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();
            System.out.println("Response: " + response.toString());
        } else {
            System.out.println("DELETE request failed. Response Code: " + responseCode);
        }
    } 
    
    //UPDATE
    private void sendEdit(String id , String n , String j , String t) throws Exception {
        //  une variable représentant l'identifiant à éditer
        String idToEdit = id;
       


        //  de nouvelles valeurs pour les champs
        String newNom = n;
		

        String newNbjrs = j;
        

        String newTauxjournalier = t;
        

        // Construire la chaîne JSON avec les nouvelles valeurs
        String jsonString = "{\"nom\":\"" + URLEncoder.encode(newNom, "UTF-8") + "\",\"nbjrs\":" + URLEncoder.encode(String.valueOf(newNbjrs), "UTF-8") + ",\"tauxjournalier\":" + URLEncoder.encode(String.valueOf(newTauxjournalier), "UTF-8") + "}";

        String urlLink = "http://localhost/server%20api/medecin/update.php?id="+idToEdit;
        URL url = new URL(urlLink);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Paramètres de la requête PUT 
        conn.setRequestMethod("PUT"); 
        conn.setRequestProperty("Content-Type", "application/json"); 
        conn.setDoOutput(true);

        // Écriture des données JSON dans le corps de la requête
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(jsonString.getBytes("UTF-8"));
        }

        // Lecture de la réponse
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            in.close();
            System.out.println("Response: " + response.toString());
        } else {
            System.out.println("request failed. Response Code: " + responseCode);
        }
    } 
    
}
class Data {
    private String title;
    private Long id;
    private Boolean children;
    private List<Data> groups;

    public String getTitle() { return title; }
    public Long getId() { return id; }
    public Boolean getChildren() { return children; }
    public List<Data> getGroups() { return groups; }

    public void setTitle(String title) { this.title = title; }
    public void setId(Long id) { this.id = id; }
    public void setChildren(Boolean children) { this.children = children; }
    public void setGroups(List<Data> groups) { this.groups = groups; }
    
    public String toString() {
        return String.format("title:%s,id:%d,children:%s,groups:%s", title, id, children, groups);
    }
}