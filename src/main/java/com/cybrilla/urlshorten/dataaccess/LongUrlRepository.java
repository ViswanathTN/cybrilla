package com.cybrilla.urlshorten.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybrilla.urlshorten.model.Url;

public interface LongUrlRepository extends JpaRepository<Url, Long>{

}
