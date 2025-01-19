/*
 * This class is used to give the user different items/gear that they can equip
 * the increase/change their stats. There are 5 different items, being a bow, helmet,
 * chestpiece, gloves, and boots. A piece of gear is created will random stats after
 * every room completion, and can also be bought at the shop. Each Gear has 3 stats,
 * and these stat values do different things depending on whether the item is a bow or
 * armour.
 */
package finalproject;

/**
 *
 * @author geoff
 */
public class Gear {
    
    private int item;
    private int stat1;
    private int stat2;
    private int stat3;
    private int rarity;
    private int orderAcquired;
    private int sellPrice;
    
    public Gear(int item, int stat1, int stat2, int stat3, int rarity, int orderAcquired){
        this.item = item;
        this.stat1 = stat1;
        this.stat2 = stat2;
        this.stat3 = stat3;
        this.rarity = rarity;
        this.orderAcquired = orderAcquired;        
        if(item==0){
            this.sellPrice = (int)(0.5*(stat1+stat2+stat3));
        }
        else{
            this.sellPrice = (int)(0.5*((stat1/2)+(stat2/2)+stat3));
        }
    }
    
    public int getItem(){
        return this.item;
    }
    public int getStat1(){
        return this.stat1;
    }
    public int getStat2(){
        return this.stat2;
    }
    public int getStat3(){
        return this.stat3;
    }
    public int getRarity(){
        return this.rarity;
    }
    public int getOrderAcquired(){
        return this.orderAcquired;
    }
    public int getSellPrice(){
        return this.sellPrice;
    }
}
