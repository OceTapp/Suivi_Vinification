package database.util;
/**
 * Callback pour les tâches asyncrones
 */
public interface OnAsyncEventListener {

        void onSuccess();
        void onFailure(Exception e);
    }

