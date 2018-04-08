package comparator;

import java.util.Comparator;

import wayde.bean.Activite;

public class ComparatorDistanceActivite  implements Comparator<Activite> {
    @Override
    public int compare(Activite activite1, Activite activite2) {


//        if (Outils.personneConnectee.getDistanceActivite(activite1) <
//                Outils.personneConnectee.getDistanceActivite(activite2)) return -1;
//        else
            return 1;
    }

}
