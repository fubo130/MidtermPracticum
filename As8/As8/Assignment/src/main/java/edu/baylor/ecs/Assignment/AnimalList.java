package edu.baylor.ecs.Assignment;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "Animals")
public class AnimalList {
	private ArrayList<Animal> AnimalList = new ArrayList<Animal>();
	public ArrayList<Animal> getAnimalList() {
		return AnimalList;
	}
	public void setAnimalList(ArrayList<Animal> animalList) {
		AnimalList = animalList;
	}
}
