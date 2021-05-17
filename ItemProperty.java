import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ItemProperty extends Property
{
    private int id;
    private String[] flags;
    private String name;
    private int volume;//ml
    private ImageIcon image;
    private Weapon weapon;
    public ItemProperty(int id, String[] flags, String name, int volume, ImageIcon image, Weapon weapon){
        this.id=id;
        this.flags=flags;
        this.name=name;
        this.volume=volume;
        this.image=image;
        this.weapon=weapon;
    }
    public ItemProperty(ItemProperty copy){
        this.id=copy.id;
        this.flags=copy.flags;
        this.name=copy.name;
        this.volume=copy.volume;
        this.image=copy.image;
        this.weapon=copy.weapon;
    }
    public ImageIcon getImage()
    {
        return this.image;
    }
}
