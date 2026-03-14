package com.example.demo.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorite")
@Data
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "instrument_uid", referencedColumnName = "uid", nullable = false)
    private Instrument instrument;

    @Column(name = "added_at", nullable = false)
    private LocalDateTime addedAt = LocalDateTime.now();

    @Column(name = "notes", length = 500)
    private String notes;

    public Favorite() {
    }

    public Favorite(User user, Instrument instrument) {
        this.user = user;
        this.instrument = instrument;
        this.addedAt = LocalDateTime.now();
    }

    public Favorite(User user, Instrument instrument, String notes) {
        this.user = user;
        this.instrument = instrument;
        this.notes = notes;
        this.addedAt = LocalDateTime.now();
    }
}
