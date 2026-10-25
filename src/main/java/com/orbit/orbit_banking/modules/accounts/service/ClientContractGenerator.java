package com.orbit.orbit_banking.modules.accounts.service;

import com.itextpdf.text.Document;
import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;

public interface ClientContractGenerator {
    /**
     *
     * @param applicationRequest
     * @return
     */
    Document generateClientContract(ApplicationRequest applicationRequest);
}
