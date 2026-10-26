package com.orbit.orbit_banking.modules.accounts.service.implementation;

import com.itextpdf.text.Document;
import com.orbit.orbit_banking.modules.accounts.model.ApplicationRequest;
import com.orbit.orbit_banking.modules.accounts.service.ClientContractGenerator;
import org.springframework.stereotype.Service;

@Service
public class ClientContractGeneratorImpl implements ClientContractGenerator {


    @Override
    public Document generateClientContract(ApplicationRequest applicationRequest) {
        return null;
    }

    private void store(Document document, String filename) {}
}
