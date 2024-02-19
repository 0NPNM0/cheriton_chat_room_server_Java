package model;

public interface MessageType {
	final String LOGIN_VALIDATE_SUCCESS = "1";
	final String LOGIN_VALIDATE_FAILURE = "2";
	final String COMMON_CHAT_MESSAGE = "3";
	final String REQUEST_ONLINE_FRIEND ="4";
	final String REPONSE_ONLINE_FRIEND ="5";
	final String NEW_ONLINE_FRIEND_TO_SERVER="6";
	final String NEW_ONLINE_FRIEND_TO_CLIENT="7";
	final String USER_REGISTER_SUCCESS = "8";
	final String USER_REGISTER_FAILURE = "9";
	final String ADD_NEW_FRIEND = "10";
	final String ADD_NEW_FRIEND_SUCCESS = "11";
	final String ADD_NEW_FRIEND_FAILURE_NO_USER = "12";
	final String ADD_NEW_FRIEND_FAILURE_ALREADY_FRIEND = "13";
}
