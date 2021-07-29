package ru.job4j.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format(
                    "Notification %s to email %s", user.getUserName(), user.getEmail());
            String body = String.format(
                    "Add a new event to %s", user.getUserName());
            send(subject, body, user.getEmail());
        });
    }

    private void send(String subject, String body, String email) {

    }

    public void close() {
        pool.shutdown();
    }

   public static class User {

        private String userName;
        private String email;

       public User(String userName, String email) {
           this.userName = userName;
           this.email = email;
       }

       public String getUserName() {
           return userName;
       }

       public String getEmail() {
           return email;
       }

       @Override
       public String toString() {
           return "User{"
                   + "userName='" + userName + '\''
                   + ", email='" + email + '\''
                   + '}';
       }
   }
}
