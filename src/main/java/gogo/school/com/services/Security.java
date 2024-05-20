package gogo.school.com.services;

public interface Security {
    /**
     * @param password
     * @return hashPassword
     */
    public String hashPassword(String password);
    public boolean verifyPassword(String rawPassword, String encodedPassword);
}
