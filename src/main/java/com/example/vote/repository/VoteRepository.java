package com.example.vote.repository;

import com.example.vote.repository.dto.voteDetail.VoteDetail;
import com.example.vote.repository.entity.Vote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VoteRepository extends ReactiveCrudRepository<Vote, Long> {

    public Flux<Vote> findByGroupId(Long groupId, Pageable pageable);

    @Query("select vote_id , created_time, title, group_id,\n" +
            "to_jsonb(array(select json_build_object( 'option_id', to2.option_id, 'play_time', to2.play_time, 'vote_id', to2.vote_id,'vote_members', array(select user_id from times_vote_submission vts where vts.vote_id = to2.vote_id and vts.option_id = to2.option_id))from times_options to2 where to2.vote_id = v.vote_id) )as times_options,\n" +
            "to_jsonb(array(select json_build_object( 'option_id', dos.option_id, 'play_date', dos.play_date, 'vote_id', dos.vote_id,'vote_members', array(select user_id from date_vote_submission dts where dts.vote_id = dos.vote_id and dts.option_id = dos.option_id)) from date_options dos where dos.vote_id = v.vote_id)) as date_options,\n" +
            "to_jsonb(array(select json_build_object( 'option_id', los.option_id, 'play_location', los.play_location, 'vote_id', los.vote_id,'vote_members', array(select user_id from location_vote_submission lts where lts.vote_id = los.vote_id and lts.option_id = los.option_id)) from location_options los where los.vote_id = v.vote_id)) as location_options,\n" +
            "to_jsonb(array(select json_build_object( 'option_id', co.option_id, 'play_category', co.play_category, 'vote_id', co.vote_id,'vote_members', array(select user_id from category_vote_submission cts where cts.vote_id = co.vote_id and cts.option_id = co.option_id)) from category_options co where co.vote_id = v.vote_id)) as category_options\n" +
            "from vote v where v.vote_id = :voteId;")
    public Mono<VoteDetail> getVoteDetail( @Param("voteId") Long voteId);

    public Mono<Boolean> existsByVoteIdAndGroupId(Long voteId, Long groupId);
}
