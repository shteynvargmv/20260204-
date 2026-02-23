package com.example.demo.repository;

import com.example.demo.entity.Favorite;
import com.example.demo.entity.User;
import com.example.demo.entity.Instrument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

//  CREATE
//  Favorite save(Favorite favorite);

    //  READ
    List<Favorite> findByUser(User user);

    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId AND f.instrument.uid = :instrumentUid")
    Optional<Favorite> findByUserIdAndInstrumentUid(
            @Param("userId") Long userId,
            @Param("instrumentUid") String instrumentUid);

    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId ORDER BY f.addedAt DESC")
    List<Favorite> findByUserIdOrderByAddedAtDesc(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId ORDER BY f.addedAt DESC")
    List<Favorite> findRecentByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT f.instrument FROM Favorite f WHERE f.user.id = :userId")
    List<Instrument> findInstrumentsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM Favorite f WHERE f.user.id = :userId")
    List<Favorite> findByUserId(@Param("userId") Long userId);

    @Query("SELECT f FROM Favorite f WHERE f.user.username = :username")
    List<Favorite> findByUsername(@Param("username") String username);

    Optional<Favorite> findByUserAndInstrument(User user, Instrument instrument);

    boolean existsByUserAndInstrument(User user, Instrument instrument);

    //  UPDATE
    @Modifying
    @Transactional
    @Query("UPDATE Favorite f SET f.notes = :notes WHERE f.user.id = :userId AND f.instrument.uid = :instrumentUid")
    int updateNotesByUserIdAndInstrumentUid(
            @Param("userId") Long userId,
            @Param("instrumentUid") String instrumentUid,
            @Param("notes") String notes);

    @Modifying
    @Transactional
    @Query("UPDATE Favorite f SET f.notes = :notes WHERE f.id = :id")
    int updateNotesById(@Param("id") Long id, @Param("notes") String notes);


    //  DELETE
    @Transactional  // <-- Добавить здесь!
    @Modifying
    int deleteByUserAndInstrument(User user, Instrument instrument);

    @Modifying
    @Transactional
    @Query("DELETE FROM Favorite f WHERE f.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Favorite f WHERE f.instrument.uid = :instrumentUid")
    void deleteAllByInstrumentUid(@Param("instrumentUid") String instrumentUid);


}