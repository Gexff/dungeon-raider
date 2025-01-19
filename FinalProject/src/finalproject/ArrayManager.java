/*
 * This class is responsible for inserting and deleting different objects in arrays,
 * and can find the index of an enemy based on its identifier.
 * It also sorts an array of Gear based on either their IDs, Rarity, or Item type, 
 * using merge sort.
 */
package finalproject;

/**
 *
 * @author geoff
 */
public class ArrayManager {
    public Projectile[] deleteProjectile(Projectile[] array, int index){
        // new array
        Projectile[] newArray = new Projectile[array.length-1];
        boolean copying = true;
        
        // index of old deck
        int i = 0;
        
        // index of new deck
        int j = 0;
        
        if(!(index > array.length-1)){
            while(copying){
                // Checks of the index to be deleted is running through the loop
                if(i == index){
                    // Checks if the index isn't the last index of the array
                    if(i < array.length-1){
                        i++;
                        newArray[j] = array[i];
                    }
                }else{
                    newArray[j] = array[i];
                }

                // Stops loop at the end of the array
                if(i==newArray.length){
                    copying = false;
                }
                i++;
                j++;
            }
        }
        else{
            System.out.println("Index to delete is out of bounds!");
            return array;
        }
        return newArray;
    }
    
    // Returns a new array that has the given card at the given index, with everything 
    // from the old array starting at the index shifted over one.
    public Projectile[] insertProjectile(Projectile[] array, int index, Projectile projectile){
        Projectile[] newArray = new Projectile[array.length+1];
        boolean copying = true;
        
        // index of old deck
        int i = 0;
        
        // index of new deck
        int j = 0;
        
        // Checks if the desired input index fits in the created array
        if(!(index >= newArray.length)){
            while(copying){
                // Checks of the index to be inserted is running through the loop
                if(j == index){                    
                    newArray[j] = projectile;
                    j++;
                }else{
                    newArray[j] = array[i];
                    i++;
                    j++;
                }

                // Stops loop at the end of the array
                if(j==newArray.length){
                    copying = false;
                }
            }
        }
        else{
            System.out.println("Index to insert is out of bounds!");
            return array;
        }
        return newArray;
    }
    public PlayerCollisionBox[] deletePlayerCollisionBox(PlayerCollisionBox[] array, int index){
        // new array
        PlayerCollisionBox[] newArray = new PlayerCollisionBox[array.length-1];
        boolean copying = true;
        
        // index of old deck
        int i = 0;
        
        // index of new deck
        int j = 0;
        
        if(!(index > array.length-1)){
            while(copying){
                // Checks of the index to be deleted is running through the loop
                if(i == index){
                    // Checks if the index isn't the last index of the array
                    if(i < array.length-1){
                        i++;
                        newArray[j] = array[i];
                    }
                }else{
                    newArray[j] = array[i];
                }

                // Stops loop at the end of the array
                if(i==newArray.length){
                    copying = false;
                }
                i++;
                j++;
            }
        }
        else{
            System.out.println("Index to delete is out of bounds!");
            return array;
        }
        return newArray;
    }
    
    // Returns a new array that has the given PlayerCollisionBox at the given index, with everything 
    // from the old array starting at the index shifted over one.
    public PlayerCollisionBox[] insertPlayerCollisionBox(PlayerCollisionBox[] array, int index, PlayerCollisionBox playerCollisionBox){
        PlayerCollisionBox[] newArray = new PlayerCollisionBox[array.length+1];
        boolean copying = true;
        
        // index of old deck
        int i = 0;
        
        // index of new deck
        int j = 0;
        
        // Checks if the desired input index fits in the created array
        if(!(index >= newArray.length)){
            while(copying){
                // Checks of the index to be inserted is running through the loop
                if(j == index){                    
                    newArray[j] = playerCollisionBox;
                    j++;
                }else{
                    newArray[j] = array[i];
                    i++;
                    j++;
                }

                // Stops loop at the end of the array
                if(j==newArray.length){
                    copying = false;
                }
            }
        }
        else{
            System.out.println("Index to insert is out of bounds!");
            return array;
        }
        return newArray;
    }
    
    // Inserts an enemy at the given index
    public Enemy[] insertEnemy(Enemy[] array, int index, Enemy enemy){
        Enemy[] newArray = new Enemy[array.length+1];
        boolean copying = true;
        
        // index of old deck
        int i = 0;
        
        // index of new deck
        int j = 0;
        
        // Checks if the desired input index fits in the created array
        if(!(index >= newArray.length)){
            while(copying){
                // Checks of the index to be inserted is running through the loop
                if(j == index){                    
                    newArray[j] = enemy;
                    j++;
                }else{
                    newArray[j] = array[i];
                    i++;
                    j++;
                }

                // Stops loop at the end of the array
                if(j==newArray.length){
                    copying = false;
                }
            }
        }
        else{
            System.out.println("Index to insert is out of bounds!");
            return array;
        }
        return newArray;
    }
    
    // Deletes the enem,y at the given index
    public Enemy[] deleteEnemy(Enemy[] array, int index){
        // new array
        Enemy[] newArray = new Enemy[array.length-1];
        boolean copying = true;
        
        // index of old deck
        int i = 0;
        
        // index of new deck
        int j = 0;
        
        if(!(index > array.length-1)){
            while(copying){
                // Checks of the index to be deleted is running through the loop
                if(i == index){
                    // Checks if the index isn't the last index of the array
                    if(i < array.length-1){
                        i++;
                        newArray[j] = array[i];
                    }
                }else{
                    newArray[j] = array[i];
                }

                // Stops loop at the end of the array
                if(i==newArray.length){
                    copying = false;
                }
                i++;
                j++;
            }
        }
        else{
            System.out.println("Index to delete is out of bounds!");
            return array;
        }
        return newArray;
    }
    
    // Inserts gear at the given index
    public Gear[] insertGear(Gear[] array, int index, Gear gear){
        Gear[] newArray = new Gear[array.length+1];
        boolean copying = true;
        
        // index of old deck
        int i = 0;
        
        // index of new deck
        int j = 0;
        
        // Checks if the desired input index fits in the created array
        if(!(index >= newArray.length)){
            while(copying){
                // Checks of the index to be inserted is running through the loop
                if(j == index){                    
                    newArray[j] = gear;
                    j++;
                }else{
                    newArray[j] = array[i];
                    i++;
                    j++;
                }

                // Stops loop at the end of the array
                if(j==newArray.length){
                    copying = false;
                }
            }
        }
        else{
            System.out.println("Index to insert is out of bounds!");
            return array;
        }
        return newArray;
    }
    
    // Deletes gear at the given index
    public Gear[] deleteGear(Gear[] array, int index){
        // new array
        Gear[] newArray = new Gear[array.length-1];
        boolean copying = true;
        
        // index of old deck
        int i = 0;
        
        // index of new deck
        int j = 0;
        
        if(!(index > array.length-1)){
            while(copying){
                // Checks of the index to be deleted is running through the loop
                if(i == index){
                    // Checks if the index isn't the last index of the array
                    if(i < array.length-1){
                        i++;
                        newArray[j] = array[i];
                    }
                }else{
                    newArray[j] = array[i];
                }

                // Stops loop at the end of the array
                if(i==newArray.length){
                    copying = false;
                }
                i++;
                j++;
            }
        }
        else{
            System.out.println("Index to delete is out of bounds!");
            return array;
        }
        return newArray;
    }
    
    // Returns the index of a given enemy by it's identifier value in a array deck
    public int findByValue(Enemy[] array, int value){
        boolean found = false;
        // Counter for returning the index
        int i = -1;
        
            // Loop that will run until the card is found by comparing symbols
            while(!found){
                i++;
                if(value==((array[i]).getIdentifier())){
                    found = true;
                }
            }
        return i;
    }   
    
    
    public void merge(Gear[] array, int l, int m, int r, int type) 
    { 
        // Find sizes of two subarrays to be merged 
        int n1 = m - l + 1; 
        int n2 = r - m; 
  
        // Create temp arrays
        Gear[] left = new Gear[n1]; 
        Gear[] right = new Gear[n2]; 
  
        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i) 
            left[i] = array[l + i]; 
        for (int j=0; j<n2; ++j) 
            right[j] = array[m + 1+ j]; 
             
        // Initial indexes of first and second arrays 
        int i = 0;
        int j = 0; 
  
        // Initial index of merged array 
        int k = l; 
        while(i < n1 && j < n2) 
        { 
            if(type == 0){
                if (left[i].getOrderAcquired() >= right[j].getOrderAcquired()) 
                { 
                    array[k] = left[i]; 
                    i++; 
                } 
                else
                { 
                    array[k] = right[j]; 
                    j++; 
                } 
                k++; 
            }
            else if(type == 1){
                if (left[i].getItem() <= right[j].getItem()) 
                { 
                    array[k] = left[i]; 
                    i++; 
                } 
                else
                { 
                    array[k] = right[j]; 
                    j++; 
                } 
                k++; 
            }
            else if(type == 2){
                if (left[i].getRarity() >= right[j].getRarity()) 
                { 
                    array[k] = left[i]; 
                    i++; 
                } 
                else
                { 
                    array[k] = right[j]; 
                    j++; 
                } 
                k++; 
            }
        } 
  
        // Copy remaining elements of left
        while(i < n1) 
        { 
            array[k] = left[i]; 
            i++; 
            k++; 
        } 
  
        // Copy remaining elements of right
        while(j < n2) 
        { 
            array[k] = right[j]; 
            j++; 
            k++; 
        } 
    } 
  
    // Sorts the array with merge sort by splitting and merging arrays
    public void sort(Gear[] gear, int l, int r, int type) 
    { 
        if (l < r) 
        { 
            //middle
            int m = (l+r)/2; 
  
            // Sort both halves 
            sort(gear, l, m, type); 
            sort(gear , m+1, r, type); 
  
            // Merge the sorted halves 
            merge(gear, l, m, r, type); 
        } 
    } 
    
    
}
