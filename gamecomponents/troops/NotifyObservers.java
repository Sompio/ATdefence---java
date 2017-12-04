package gamecomponents.troops;

import gamecomponents.towers.TroopObserver;

/**
 * Created by Pierre on 2016-12-02.
 */

/**
 * Interface NotifyObservers specifies the necessary methods to handle
 * the notifications.
 */

public interface NotifyObservers {
    void addObserver(TroopObserver observer);

    void removeObserver(TroopObserver observer);

    void notifyObserver(Troop troop);
}
