package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.model.Firestation;

import java.util.List;

public interface FirestationService {

    /**
     * Find all firestations
     *
     * @return list with all firestations
     */
    List<Firestation> getAllFirestations();

    /**
     * Find firestation number by address
     *
     * @param address for which the firestation number is sought
     * @return the number of firestation associate
     */
    Firestation findFirestationByAddress(String address);

    /**
     * Update a firestation
     *
     * @param firestation number need to update by address
     * @return firestation updated
     */
    Firestation updateFirestation(Firestation firestation);


    /**
     * Create firestation
     *
     * @param firestation to create
     * @return firestation created
     */
    Firestation createNewFirestation(Firestation firestation);

    /**
     * Delete firestation by address
     *
     * @param address of firestation to delete
     */
    void deleteFirestation(String address);
}
