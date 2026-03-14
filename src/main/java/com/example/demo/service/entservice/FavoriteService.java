package com.example.demo.service.entservice;

import com.example.demo.entity.Favorite;
import com.example.demo.entity.Instrument;
import com.example.demo.entity.User;
import com.example.demo.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    @Autowired
    FavoriteRepository favoriteRepository;

    public Favorite findByUserIdAndInstrumentUid(Long userId, String instrumentUid){
        Optional<Favorite> favoriteOpt = favoriteRepository.findByUserIdAndInstrumentUid(userId,instrumentUid);
        return favoriteOpt.orElse(null);
    }

    public List<Instrument> findInstrumentsByUserId(Long userId){
        return favoriteRepository.findInstrumentsByUserId(userId);
    }

    public Favorite save(Favorite favorite){
        Optional<Favorite> favoriteOpt = favoriteRepository.findByUserIdAndInstrumentUid(favorite.getUser().getId(), favorite.getInstrument().getUid());
        return favoriteOpt.orElseGet(() -> favoriteRepository.save(favorite));
    }

    public Boolean deleteByUserAndInstrument(User user, Instrument instrument){
        return favoriteRepository.deleteByUserAndInstrument(user, instrument) != 0;
    }

    public List<Favorite> findByUsername(String username){
        return favoriteRepository.findByUsername(username);
    }


}
