package com.safetynet.alerts.api.dao;

import com.safetynet.alerts.api.config.DataSource;
import com.safetynet.alerts.api.model.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirestationDaoImpl implements FirestationDao {

    @Autowired
    private DataSource dataSource;

    /**
     * Find all firestations
     *
     * @return list with all firestations
     */
    @Override
    public List<Firestation> findFirestations() {
        return dataSource.getAllFirestation();
    }

    /**
     * Find firestation by address
     *
     * @param address for which the firestation number is sought
     * @return the firestation associate
     */
    @Override
    public Firestation findByAddress(String address) {
        List<Firestation> firestations = dataSource.getAllFirestation();
        for (Firestation firestation : firestations) {
            if (firestation.getAddress().equalsIgnoreCase(address)) {
                return firestation;
            }
        }
        return null;
    }

    /**
     * Find a list of firestation found by station number
     *
     * @param stationNumber for which the firestation number is sought
     * @return a list of firestation found by station number
     */
    @Override
    public List<Firestation> findByStationNumber(String stationNumber) {
        List<Firestation> firestations = dataSource.getAllFirestation();
        List<Firestation> firestationList = new ArrayList<>();
        for (Firestation firestation : firestations) {
            if (firestation.getStationNumber().equalsIgnoreCase(stationNumber)) firestationList.add(firestation);
        }
        if (firestationList.isEmpty()) return null;
        return firestationList;
    }

    /**
     * Save firestation
     *
     * @param firestation to save
     * @return firestation saved
     */
    @Override
    public Firestation save(Firestation firestation) {
        List<Firestation> firestationList = dataSource.getAllFirestation();
        Firestation firestation1 = findByAddress(firestation.getAddress());
        if (firestation1 == null) firestationList.add(firestation);
        else {
            int of = firestationList.indexOf(firestation1);
            firestationList.remove(of);
            firestationList.add(of, firestation);
        }
        return firestation;
    }

    /**
     * Delete firestation by address
     *
     * @param address of firestation to delete
     */
    @Override
    public void delete(String address) {
        List<Firestation> firestationList = dataSource.getAllFirestation();
        firestationList.removeIf(elem -> elem.getAddress().equalsIgnoreCase(address));
    }

}
