package ac.id.unikom.perpusif.Helpers;

import ac.id.unikom.perpusif.Models.Petugas;
import ac.id.unikom.perpusif.Models.Role;

/**
 * @author Bagas
 */
public class SessionHelper {
    private static SessionHelper instance;
    private Petugas Petugas;

    private SessionHelper() {
    }

    public static synchronized SessionHelper getInstance() {
        if (instance == null) {
            instance = new SessionHelper();
        }
        return instance;
    }

    public boolean isLogin() {
        return Petugas != null;
    }

    public void setPetugas(Petugas u) {
        Petugas = u;
    }

    public Petugas getPetugas() {
        if (Petugas == null) {
            throw new Error("Petugas tidak ditemukan");
        }
        return Petugas;
    }

    public void logout() {
        Petugas = null;
    }

    public boolean isAdmin() {
        return getPetugas().getRole() == Role.admin;
    }
}
