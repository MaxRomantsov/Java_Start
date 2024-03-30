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
        //showCategories();
        //editCategory();
        //deleteCategory();
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

    private static void editCategory() {
        Scanner scanner = new Scanner(System.in);
        Calendar calendar = Calendar.getInstance();
        SessionFactory sf = HibernateUtil.getSessionFactory();

        try (Session session = sf.openSession()) {
            session.beginTransaction();

            System.out.println("Введіть ID категорії для редагування:");
            int id = scanner.nextInt();

            Category category = session.get(Category.class, id);

            if (category != null) {
                System.out.println("Вкажіть нову назву:");
                String newName = scanner.nextLine();
                scanner.nextLine();
                category.setName(newName);
                System.out.println("Вкажіть нове фото:");
                String newImage = scanner.nextLine();
                category.setImage(newImage);
                session.update(category);
                System.out.println("Категорію оновлено успішно!");
            } else {
                System.out.println("Категорія не знайдена!");
            }

            session.getTransaction().commit();
        }
    }

    private static void deleteCategory() {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        try (Session session = sf.openSession()) {
            session.beginTransaction();

            System.out.println("Введіть ID категорії для видалення:");
            int id = new Scanner(System.in).nextInt();

            Category category = session.get(Category.class, id);

            if (category != null) {
                session.delete(category);
                System.out.println("Категорію видалено успішно!");
            } else {
                System.out.println("Категорія не знайдена!");
            }

            session.getTransaction().commit();
        }
    }
}