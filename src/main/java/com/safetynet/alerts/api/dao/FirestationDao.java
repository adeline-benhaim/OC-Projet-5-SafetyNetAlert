package com.safetynet.alerts.api.dao;

import com.safetynet.alerts.api.model.Firestation;

import java.util.List;

public interface FirestationDao {

    /**
     * Find all firestations
     * @return list with all firestations
     */
    List<Firestation> findFirestations();

    /**
     * Find firestation number by address
     * @param address for which the firestation number is sought
     * @return the number of firestation associate
     */
    Firestation findByAddress(String address);

    /**
     * Find a list of firestation found by station number
     * @param stationNumber for which the firestation number is sought
     * @return a list of firestation found by station number
     */
    List<Firestation> findByStationNumber(String stationNumber);

    /**
     * Save firestation
     * @param firestation to save
     * @return firestation saved
     */
    Firestation save(Firestation firestation);


    /**
     * Delete firestation by address or by station number
     * @param address of firestation to delete
     */
    void delete(String address);

}
