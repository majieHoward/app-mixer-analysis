package com.howard.www.core.web.security.authorizing.service;

import com.howard.www.core.data.transfer.dto.IDataTransferObject;

public interface IValidationLoginDataValidity {
    public String validationDataValidity(IDataTransferObject queryParameters)throws Exception;
}
