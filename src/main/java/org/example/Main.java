package org.example;

import org.example.entities.Category;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        //insertCategory();
        showCategories();
    }


    private static void insertCategory() {
        Scanner scanner = new Scanner(System.in);
        Calendar calendar = Calendar.getInstance();
        var sf = HibernateUtil.getSessionFactory();
        try(Session session = sf.openSession()) {
            session.beginTransaction();
            Category category = new Category();
            System.out.println("Вкажіть назву");
            category.setName(scanner.nextLine());
            System.out.println("Вкажіть фото");
            category.setImage(scanner.nextLine());
            category.setDateCreated(calendar.getTime());
            session.save(category);
            session.getTransaction().commit();
        }
    }

    private static void showCategories() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        try(Session session = sf.openSession()) {
            session.beginTransaction();

            List<Category> list = session.createQuery("from Category", Category.class)
                    .getResultList();

            for(var item : list) {
                System.out.println(item);
            }

            session.getTransaction().commit();
        }
    }
}