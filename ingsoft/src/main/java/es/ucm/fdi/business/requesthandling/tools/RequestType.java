package es.ucm.fdi.business.requesthandling.tools;

import es.ucm.fdi.business.FrontController;

/**
 * Enum representing the different types of requests that
 * the {@link FrontController} can handle.
 */
public enum RequestType {
    REGISTER_ACCOUNT,
    REMOVE_ACCOUNT,

    ADD_REVIEW,
    REMOVE_REVIEW,
    MODIFY_CLUB,
    MODIFY_USER,

    SEARCH,
    BUY;
}