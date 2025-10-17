package com.example.attack_on_monday_backend.meeting.repository;

import com.example.attack_on_monday_backend.meeting.entity.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    @Modifying
    @Transactional
    @Query("delete from Meeting m where m.id = :id and (:version is null or m.version = :version)")
    int hardDeleteByIdAndVersion(@Param("id") Long id, @Param("version") Long version);

    Optional<Meeting> findByPublicId(String publicId);
    boolean existsByPublicId(String publicId);

    @EntityGraph(attributePaths = {
            "creator",
            "project",
            "note"
    })
    Optional<Meeting> findDetailByPublicId (String publicId);

    // 리스트
    @Query("""
        select m from Meeting m
        where (m.creator.id = :accountId
           or exists (select 1 from MeetingParticipant mp
                      where mp.meeting = m and mp.account.id = :accountId))
        order by m.updatedAt desc
    """)
    Page<Meeting> findPageVisibleTo(@Param("accountId") Long accountId, Pageable pageable);

    // 캘린더
    @Query("""
        select m from Meeting m
        where (m.creator.id = :accountId
           or exists (select 1 from MeetingParticipant mp
                      where mp.meeting = m and mp.account.id = :accountId))
          and (
            (m.startTime is not null and m.endTime is not null and m.startTime < :to and m.endTime > :from)
            or (m.allDay = true and m.startTime is null and m.endTime is null)
          )
        order by coalesce(m.startTime, m.updatedAt) asc
    """)
    List<Meeting> findRangeVisibleTo(@Param("accountId") Long accountId,
                                     @Param("from") LocalDateTime from,
                                     @Param("to") LocalDateTime to);
}
