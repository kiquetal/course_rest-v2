package py.com.edge.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;



@SoapFault(faultCode = FaultCode.SERVER)
public class InvalidRequest extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7120453676307018570L;

  public InvalidRequest(String message)
  {
	  super(message);
  }
}
