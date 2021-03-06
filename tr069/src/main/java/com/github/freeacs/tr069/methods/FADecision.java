package com.github.freeacs.tr069.methods;

import com.github.freeacs.base.Log;
import com.github.freeacs.dbi.util.ProvisioningMessage;
import com.github.freeacs.dbi.util.ProvisioningMessage.ErrorResponsibility;
import com.github.freeacs.dbi.util.ProvisioningMessage.ProvStatus;
import com.github.freeacs.dbi.util.ProvisioningMode;
import com.github.freeacs.http.HTTPRequestResponseData;
import com.github.freeacs.tr069.SessionData;
import com.github.freeacs.tr069.xml.Fault;
import java.util.List;

public class FADecision {
  public static void process(HTTPRequestResponseData reqRes) {
    SessionData sessionData = reqRes.getSessionData();
    if (sessionData.getUnit().getProvisioningMode() == ProvisioningMode.REGULAR) {
      List<HTTPRequestResponseData> reqResList = sessionData.getReqResList();
      if (reqResList != null && reqResList.size() >= 3) {
        HTTPRequestResponseData prevReqRes = reqResList.get(reqResList.size() - 2);
        HTTPRequestResponseData prev2ReqRes = reqResList.get(reqResList.size() - 3);
        String prevMethod = prevReqRes.getResponseData().getMethod();
        if (TR069Method.GET_PARAMETER_VALUES.equals(prevMethod)) {
          String prev2Method = prev2ReqRes.getResponseData().getMethod();
          if (!TR069Method.GET_PARAMETER_VALUES.equals(prev2Method)) {
            Log.warn(
                FADecision.class,
                "GPVres contained error, try once more and ask for all parameters");
            reqRes.getResponseData().setMethod(TR069Method.GET_PARAMETER_VALUES);
            return;
          }
        }
      }
    }

    ProvisioningMessage pm = sessionData.getProvisioningMessage();
    Fault fault = reqRes.getRequestData().getFault();
    pm.setErrorCode(Integer.valueOf(fault.getFaultCode()));
    pm.setErrorMessage(fault.getFaultString());
    pm.setErrorResponsibility(ErrorResponsibility.CLIENT);
    pm.setProvStatus(ProvStatus.ERROR);
    reqRes.getResponseData().setMethod(TR069Method.EMPTY);
  }
}
