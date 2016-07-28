package org.mishpaha.project.data.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.util.View;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON_DETAILS")
public class PersonDetails {

    @Column
    private int personId;

    private int groupId;
    @JsonView(View.Summary.class)
    private int categoryId;

    @JsonView(View.Summary.class)
    private Boolean givesTithe;

}
