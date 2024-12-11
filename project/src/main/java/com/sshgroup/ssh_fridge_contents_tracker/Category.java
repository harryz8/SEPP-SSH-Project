package com.sshgroup.ssh_fridge_contents_tracker;


import jakarta.persistence.*;

@Entity
@Table(name = "category_table")
public class Category {
    @Id
    int category_id;

    @Column
    private String category_name;

    public Category(){
    }
    public Category(int category_id, String category_name){
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean equals(Category otherCategory) {
        if (otherCategory == null) {
            return false;
        }
        return (this.getCategory_id() == otherCategory.getCategory_id());
    }
}
