package com.hansol.handa.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hansol.handa.domain.ChallengeVO;
import com.hansol.handa.mapper.ChallengeMapper;
import ch.qos.logback.classic.Logger;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Value("${APIKEY}")
    private String APIKEY;
    @Autowired
    private ChallengeMapper mapper;
    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    /* 챌린지 리스트 조회 */
    @Override
    public List<ChallengeVO> selectChallegeList(String category, String sortType, String createdate, String count,
                                                String searchWord, String challengeType, int challenge_state) {

        Map<String, Object> map = new HashMap<>();
        List<ChallengeVO> list = null;

        map.put("sortType", sortType);
        map.put("createdate", createdate);				// 생성 날짜 (화면 호출한 경우 "0")
        map.put("count", count);						// 참여 인원 (화면 호출한 경우 "0")
        map.put("searchWord", searchWord);				// 검색어
        map.put("challenge_type", challengeType);		// 챌린지 타입 (기본 값 : "0")
        map.put("challenge_state", challenge_state);

        if(category.equals("0")) {	// 전체 리스트 조회
            list = mapper.selectChallengeList(map);
            list = getChallengeState(list);
        }
        else {	// 카테고리 별 리스트 조회
            int categoryID = Integer.parseInt(category);

            if(categoryID < 8) {
                map.put("subcategory_id", categoryID);

                list = mapper.selectChallegeListCategory(map);
            }
            else {
                map.put("category_id", categoryID);

                list = mapper.selectChallegeListMainCategory(map);
            }
            list = getChallengeState(list);
        }

        return list;
    }

    // 챌린지 상태 설정 & 모집 중인 챌린지 디데이 설정
    public List<ChallengeVO> getChallengeState(List<ChallengeVO> list) {

        //'yyyy-MM-dd' 포맷 설정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //오늘날짜
        LocalDate today = LocalDate.now();

        for(int i = 0; i < list.size(); i++) {
            ChallengeVO vo = list.get(i);

            //비교할 date를 데이터 포맷으로 변경
            LocalDate startDate = LocalDate.parse(vo.getStartdate(), formatter);
            LocalDate endDate = LocalDate.parse(vo.getEnddate(), formatter);

            // 오늘 날짜가 시작 날짜 전이면 모집중 상태& 시작 날짜로 디데이 계산
            if(today.isBefore(startDate)) {
                int limit_member = vo.getLimit_member();
                int part_member = vo.getJoinVO().getCount();

                //모집 인원보다 참여 인원이 적거나 모집 인원이 없으면 모집중 상태
                if(limit_member == 0 || limit_member > part_member) {
                    list.get(i).setChallenge_state("모집중");
                    list.get(i).setDday((int)ChronoUnit.DAYS.between(today, startDate));
                }
                //모집 인원이 참여 인원과 같으면 모집 마감 상태
                else if(limit_member == part_member)
                    list.get(i).setChallenge_state("모집 마감");

            }
            // 오늘 날짜가 종료 날짜 이후면 종료 상태
            else if(today.isAfter(endDate))
                list.get(i).setChallenge_state("종료");
                // 오늘 날짜가 시작 날짜와 종료 날짜 사이면 진행중 상태
            else
                list.get(i).setChallenge_state("진행중");
        }

        return list;
    }

    // 카테고리 이름 조회
    @Override
    public Map<String, String> selectCategoryName(int sub_category_id) {
        return mapper.selectCategoryName(sub_category_id);
    }

    // 챌린지 개수 조회
    @Override
    public int selectCount(HashMap<String, Object> countMap) {
        return mapper.selectCount(countMap);
    }

    // 메인 페이지 인기 챌린지 조회
    @Override
    public List<ChallengeVO> selectPopular() {
        List<ChallengeVO> list = null;

        list = mapper.selectPopular();
        list = getChallengeState(list);
        list = amendList(list);

        return list;
    }

    //메인 페이지 유료 챌린지 조회
    @Override
    public List<ChallengeVO> selectCost() {
        List<ChallengeVO> list = null;

        list = mapper.selectCost();
        list = getChallengeState(list);
        list = amendList(list);

        return list;
    }

    // 메인 페이지 챌린지 리스트 수정 (생성 날짜)
    public List<ChallengeVO> amendList(List<ChallengeVO> list){
        for(ChallengeVO challenge : list) {
            // 생성 날짜 수정 (datetime -> date)
            String[] date = challenge.getCreatedate().split(" ");
            challenge.setCreatedate(date[0]);
        }

        return list;
    }

    @Override
    public List<String> getimagelist(String searchWord) {
        List<String> returnList=new ArrayList<>();
        String apiurl="https://api.pexels.com/v1/search?query="
                +searchWord+"&per_page=15&locale=ko-KR";
        try {
            URL url=new URL(apiurl);
            URLConnection conn=url.openConnection();
            conn.setRequestProperty ("Authorization", APIKEY);
            BufferedReader bf=
                    new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line=bf.readLine();
            JSONParser jsonParser=new JSONParser();
            JSONObject parserob=(JSONObject)jsonParser.parse(line);
            JSONArray jsonArray=(JSONArray)parserob.get("photos");
            for(int i=0;i<jsonArray.size();i++) {
                JSONObject photos=(JSONObject)jsonArray.get(i);
                JSONObject src=(JSONObject) photos.get("src");
                returnList.add((String)src.get("medium"));
            }


        }catch(Exception e) {
            e.printStackTrace();
        }
        return returnList;
    }

    @Override
    public void createChallenge(ChallengeVO challengeVO) {
        mapper.createChallenge(challengeVO);
        joinChallenge(challengeVO.getMember_id(),challengeVO.getChallenge_id());
    }

    @Override
    public void joinChallenge(String member_id, Integer challenge_id) {
        mapper.joinChallenge(member_id,challenge_id);
    }

    @Override
    public ChallengeVO getChallenge(int challenge_id) {
        return mapper.getChallenge(challenge_id);
    }

    @Override
    public void updateChallenge(ChallengeVO challengeVO) {
        mapper.updateChallenge(challengeVO);

    }

    @Override
    public void deleteChallenge(ChallengeVO challengeVO) {
        mapper.deleteChallenge(challengeVO);
    }

    @Override
    public void secessionChallenge(ChallengeVO challengeVO) {
        mapper.secessionChallenge(challengeVO);
    }

    @Override
    public ChallengeVO detailChallenge(int challenge_id) {
        return mapper.detailChallenge(challenge_id);
    }

}