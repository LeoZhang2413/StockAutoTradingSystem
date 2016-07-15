package com.aeolus.swinggui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import java.awt.Font;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import javax.swing.JProgressBar;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class InforWindow extends JPanel {

	private BindingGroup m_bindingGroup;
	private com.aeolus.swinggui.InfoWindowModel infoWindowModel = new com.aeolus.swinggui.InfoWindowModel();
	private JTextField closeJTextField;
	private JTextField highJTextField;
	private JTextField lowJTextField;
	private JTextField openJTextField;
	private JTextField volumeJTextField;
	private JTextField dateJTextField;
	public InforWindow(com.aeolus.swinggui.InfoWindowModel newInfoWindowModel) {
		this();
	}

	public InforWindow() {
		setLayout(null);

		JLabel closeLabel = new JLabel("Close:");
		closeLabel.setBounds(12, 5, 41, 22);
		closeLabel.setFont(new Font("Purisa", Font.BOLD, 12));
		add(closeLabel);

		closeJTextField = new JTextField();
		closeJTextField.setBounds(66, 6, 166, 19);
		add(closeJTextField);

		JLabel highLabel = new JLabel("High:");
		highLabel.setBounds(16, 38, 34, 22);
		highLabel.setFont(new Font("Purisa", Font.BOLD, 12));
		add(highLabel);

		highJTextField = new JTextField();
		highJTextField.setBounds(66, 39, 166, 19);
		add(highJTextField);

		JLabel lowLabel = new JLabel("Low:");
		lowLabel.setBounds(17, 71, 31, 22);
		lowLabel.setFont(new Font("Purisa", Font.BOLD, 12));
		add(lowLabel);

		lowJTextField = new JTextField();
		lowJTextField.setBounds(66, 72, 166, 19);
		add(lowJTextField);

		JLabel openLabel = new JLabel("Open:");
		openLabel.setBounds(14, 104, 37, 22);
		openLabel.setFont(new Font("Purisa", Font.BOLD, 12));
		add(openLabel);

		openJTextField = new JTextField();
		openJTextField.setBounds(66, 105, 166, 19);
		add(openJTextField);

		JLabel volumeLabel = new JLabel("Volume:");
		volumeLabel.setBounds(6, 137, 54, 22);
		volumeLabel.setFont(new Font("Purisa", Font.BOLD, 12));
		add(volumeLabel);

		volumeJTextField = new JTextField();
		volumeJTextField.setBounds(66, 138, 166, 19);
		add(volumeJTextField);
		
		JLabel dateLabel = new JLabel("Date:");
		dateLabel.setBounds(6, 170, 38, 22);
		dateLabel.setFont(new Font("Purisa", Font.BOLD, 12));
		add(dateLabel);
		
		dateJTextField = new JTextField();
		dateJTextField.setBounds(66, 171, 166, 19);
		add(dateJTextField);

		if (infoWindowModel != null) {
			m_bindingGroup = initDataBindings();
		}
	}

	public void setQuote(String date, String open, String close, String high, String low, String volume){
		infoWindowModel.setDate(date);
		infoWindowModel.setOpen(open);
		infoWindowModel.setClose(close);
		infoWindowModel.setHigh(high);
		infoWindowModel.setLow(low);
		infoWindowModel.setVolume(volume);
	}
	protected BindingGroup initDataBindings() {
		BeanProperty<InfoWindowModel, String> infoWindowModelBeanProperty = BeanProperty.create("close");
		BeanProperty<JTextField, String> jTextFieldBeanProperty = BeanProperty.create("text");
		AutoBinding<InfoWindowModel, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ, infoWindowModel, infoWindowModelBeanProperty, closeJTextField, jTextFieldBeanProperty);
		autoBinding.bind();
		//
		BeanProperty<InfoWindowModel, String> infoWindowModelBeanProperty_1 = BeanProperty.create("high");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_1 = BeanProperty.create("text");
		AutoBinding<InfoWindowModel, String, JTextField, String> autoBinding_1 = Bindings.createAutoBinding(UpdateStrategy.READ, infoWindowModel, infoWindowModelBeanProperty_1, highJTextField, jTextFieldBeanProperty_1);
		autoBinding_1.bind();
		//
		BeanProperty<InfoWindowModel, String> infoWindowModelBeanProperty_2 = BeanProperty.create("low");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_2 = BeanProperty.create("text");
		AutoBinding<InfoWindowModel, String, JTextField, String> autoBinding_3 = Bindings.createAutoBinding(UpdateStrategy.READ, infoWindowModel, infoWindowModelBeanProperty_2, lowJTextField, jTextFieldBeanProperty_2);
		autoBinding_3.bind();
		//
		BeanProperty<InfoWindowModel, String> infoWindowModelBeanProperty_3 = BeanProperty.create("open");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_3 = BeanProperty.create("text");
		AutoBinding<InfoWindowModel, String, JTextField, String> autoBinding_4 = Bindings.createAutoBinding(UpdateStrategy.READ, infoWindowModel, infoWindowModelBeanProperty_3, openJTextField, jTextFieldBeanProperty_3);
		autoBinding_4.bind();
		//
		BeanProperty<InfoWindowModel, String> infoWindowModelBeanProperty_4 = BeanProperty.create("volume");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_4 = BeanProperty.create("text");
		AutoBinding<InfoWindowModel, String, JTextField, String> autoBinding_5 = Bindings.createAutoBinding(UpdateStrategy.READ, infoWindowModel, infoWindowModelBeanProperty_4, volumeJTextField, jTextFieldBeanProperty_4);
		autoBinding_5.bind();
		//
		BeanProperty<InfoWindowModel, String> infoWindowModelBeanProperty_5 = BeanProperty.create("date");
		BeanProperty<JTextField, String> jTextFieldBeanProperty_5 = BeanProperty.create("text");
		AutoBinding<InfoWindowModel, String, JTextField, String> autoBinding_2 = Bindings.createAutoBinding(UpdateStrategy.READ, infoWindowModel, infoWindowModelBeanProperty_5, dateJTextField, jTextFieldBeanProperty_5);
		autoBinding_2.bind();
		//
		BindingGroup bindingGroup = new BindingGroup();
		//
		bindingGroup.addBinding(autoBinding);
		bindingGroup.addBinding(autoBinding_1);
		bindingGroup.addBinding(autoBinding_3);
		bindingGroup.addBinding(autoBinding_4);
		bindingGroup.addBinding(autoBinding_5);
		bindingGroup.addBinding(autoBinding_2);
		return bindingGroup;
	}
}
