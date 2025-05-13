package com.hibernate.gui;

import java.awt.EventQueue;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.hibernate.dao.EmpleadoDAO;
import com.hibernate.model.Empleado;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class App {

	private JFrame frame;
	private JTable table;
	private JTextField textFieldId;
	private JTextField textFieldNombre;
	private JTextField textFieldEdad;
	private JTextField textFieldFoto;
	private JLabel lblMostrarFoto;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		Empleado empleado = new Empleado();
		EmpleadoDAO empleadoDAO = new EmpleadoDAO();
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 415);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		DefaultTableModel modelTable = new DefaultTableModel();
		modelTable.addColumn("Id");
		modelTable.addColumn("Nombre");
		modelTable.addColumn("Edad");
		modelTable.addColumn("Foto");
		
		List<Empleado> empleados = empleadoDAO.selectAllEmpleados();
		
		for (Empleado emp : empleados) {
			Object[] row = new Object[4];
			row[0] = emp.getId();
			row[1] = emp.getNombre();
			row[2] = emp.getEdad();
			row[3] = emp.getFoto();
			modelTable.addRow(row);
		}
		
		JLabel lblMostrarFoto = new JLabel("");
		lblMostrarFoto.setBounds(518, 123, 235, 200);
		frame.getContentPane().add(lblMostrarFoto);
		
		table = new JTable(modelTable);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				TableModel modelUsuario = table.getModel();
				textFieldId.setText(modelUsuario.getValueAt(index, 0).toString());
				textFieldNombre.setText(modelUsuario.getValueAt(index, 1).toString());
				textFieldEdad.setText(modelUsuario.getValueAt(index, 2).toString());
				
				//Se guarda en una variable de tipo String la url de la tabla
				String urlFoto = modelUsuario.getValueAt(index, 3).toString();
				
				//Se imprime la variable en el textField correspondiente
				textFieldFoto.setText(urlFoto);
				
				try {
					//Se crea una variable de tipo URL con la url del textField
					URL url = new URL(urlFoto);
					
					//Se crea una variable de tipo ImageIcon con la variable anterior
					ImageIcon imagenURL = new ImageIcon(url);
					
					//Se escala la imagen al tamaño del JLabel
					ImageIcon imageIcon = new ImageIcon(imagenURL.getImage().getScaledInstance(
														lblMostrarFoto.getWidth(),
														lblMostrarFoto.getHeight(),
														Image.SCALE_DEFAULT));
					
					//Se muestra la imagen en el JLabel correspondiente
					lblMostrarFoto.setIcon(imageIcon);
				} catch (MalformedURLException e2) {
					//Url no correcta
					e2.printStackTrace();
					
					//Se quita la imagen si falla
					lblMostrarFoto.setIcon(null);
				}
			}
		});
		table.setBounds(12, 12, 300, 150);
		frame.getContentPane().add(table);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 12, 400, 200);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblId = new JLabel("Id:");
		lblId.setBounds(430, 13, 70, 15);
		frame.getContentPane().add(lblId);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(430, 40, 70, 15);
		frame.getContentPane().add(lblNombre);
		
		JLabel lblEdad = new JLabel("Edad:");
		lblEdad.setBounds(430, 67, 70, 15);
		frame.getContentPane().add(lblEdad);
		
		JLabel lblFoto = new JLabel("Foto:");
		lblFoto.setBounds(430, 94, 70, 15);
		frame.getContentPane().add(lblFoto);
		
		textFieldId = new JTextField();
		textFieldId.setBounds(518, 11, 114, 19);
		frame.getContentPane().add(textFieldId);
		textFieldId.setColumns(10);
		
		textFieldId.setEditable(false);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(518, 38, 114, 19);
		frame.getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldEdad = new JTextField();
		textFieldEdad.setBounds(518, 65, 114, 19);
		frame.getContentPane().add(textFieldEdad);
		textFieldEdad.setColumns(10);
		
		textFieldFoto = new JTextField();
		textFieldFoto.setBounds(518, 92, 470, 19);
		frame.getContentPane().add(textFieldFoto);
		textFieldFoto.setColumns(10);
			
			JButton botonInsertar = new JButton("Insertar");
			botonInsertar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String nombre=textFieldNombre.getText();
					
					String edadTexto=textFieldEdad.getText();
					int edad=Integer.parseInt(edadTexto);
					
					String foto=textFieldFoto.getText();
					
					Empleado empleado = new Empleado(nombre, edad, foto);
					empleadoDAO.insertEmpleado(empleado);
					
					textFieldNombre.setText("");
					textFieldEdad.setText("");
					textFieldFoto.setText("");
					
					List<Empleado> empleados = empleadoDAO.selectAllEmpleados();
					
					for (Empleado emp : empleados) {
						Object[] row = new Object[4];
						row[0] = emp.getId();
						row[1] = emp.getNombre();
						row[2] = emp.getEdad();
						row[3] = emp.getFoto();
						modelTable.addRow(row);
					}
				}
			});
			botonInsertar.setBounds(12, 224, 117, 25);
			frame.getContentPane().add(botonInsertar);
			
			JButton botonActualizar = new JButton("Actualizar");
			botonActualizar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String idtexto=textFieldId.getText();
					int id=Integer.parseInt(idtexto);
					
					String nombre=textFieldNombre.getText();
					
					String edadTexto=textFieldEdad.getText();
					int edad=Integer.parseInt(edadTexto);
					
					String foto=textFieldFoto.getText();
					
					Empleado empleado=empleadoDAO.selectEmpleadoById(id);
					empleado.setNombre(nombre);
					empleado.setEdad(edad);
					empleado.setFoto(foto);
					empleadoDAO.updateEmpleado(empleado);
					
					textFieldId.setText("");
					textFieldNombre.setText("");
					textFieldEdad.setText("");
					textFieldFoto.setText("");
					
					List<Empleado> empleados = empleadoDAO.selectAllEmpleados();
					
					for (Empleado emp : empleados) {
						Object[] row = new Object[4];
						row[0] = emp.getId();
						row[1] = emp.getNombre();
						row[2] = emp.getEdad();
						row[3] = emp.getFoto();
						modelTable.addRow(row);
					}
				}
			});
			botonActualizar.setBounds(141, 224, 117, 25);
			frame.getContentPane().add(botonActualizar);
			
			JButton botonBorrar = new JButton("Borrar");
			botonBorrar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String idtexto=textFieldId.getText();
					int id=Integer.parseInt(idtexto);
					
					empleadoDAO.deleteEmpleado(id);
					
					textFieldId.setText("");
					textFieldNombre.setText("");
					textFieldEdad.setText("");
					textFieldFoto.setText("");
					
					List<Empleado> empleados = empleadoDAO.selectAllEmpleados();
					
					for (Empleado emp : empleados) {
						Object[] row = new Object[4];
						row[0] = emp.getId();
						row[1] = emp.getNombre();
						row[2] = emp.getEdad();
						row[3] = emp.getFoto();
						modelTable.addRow(row);
					}
				}
			});
			botonBorrar.setBounds(270, 224, 117, 25);
			frame.getContentPane().add(botonBorrar);
	}
}