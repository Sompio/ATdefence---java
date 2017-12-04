package gamecomponents;

import gamecomponents.towers.TroopObserver;
import gamecomponents.troops.NotifyObservers;
import gamecomponents.troops.Troop;

import java.util.ArrayList;

/**
 * Created by Pierre on 2016-12-02.
 */
public class LandOnReceiver implements NotifyObservers {


// Uses the Subject interface to update all Observers


        private ArrayList<TroopObserver> troopObservers;

        public LandOnReceiver(){

            // Creates an ArrayList to hold all observers
            troopObservers = new ArrayList<>();
        }

        public void addObserver(TroopObserver observer) {

            // Adds a new observer to the ArrayList

            troopObservers.add(observer);

        }

        public void removeObserver(TroopObserver observer) {

            // Remove a observer

            troopObservers.remove(observer);
        }

        public void notifyObserver(Troop troop) {

            // Cycle through all observers and notifies them
            for(TroopObserver observer : troopObservers){

                observer.landOn(troop);

            }
        }

}
