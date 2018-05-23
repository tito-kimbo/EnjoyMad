package es.ucm.fdi.business.requesthandling;

import es.ucm.fdi.business.data.AnswerPOJO;
import es.ucm.fdi.business.data.RequestPOJO;

/**
 * General scheme to build new {@code RequestHandler}s implementations 
 * from {@code RequestPOJO}s, which will handle them.
 */
public interface RequestHandler extends Cloneable, Runnable {

	/**
	 * Clones the handler with the relevant parameters.
	 * 
	 * @param request
     *          - {@code RequestPOJO} with the information to be handled
     * 
	 * @return
	 */
	public Object clone(RequestPOJO rp);

	/**
	 * Handles the associated request
	 */
	public void run();
}