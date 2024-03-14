package com.todomypet.petservice.service;

import com.github.f4b6a3.ulid.UlidCreator;
import com.todomypet.petservice.domain.node.*;
import com.todomypet.petservice.domain.relationship.Adopt;
import com.todomypet.petservice.dto.*;
import com.todomypet.petservice.dto.openFeign.AchieveReqDTO;
import com.todomypet.petservice.dto.openFeign.CheckAchieveOrNotReqDTO;
import com.todomypet.petservice.dto.openFeign.CheckAchieveOrNotResDTO;
import com.todomypet.petservice.dto.pet.*;
import com.todomypet.petservice.exception.CustomException;
import com.todomypet.petservice.exception.ErrorCode;
import com.todomypet.petservice.mapper.PetMapper;
import com.todomypet.petservice.repository.AdoptRepository;
import com.todomypet.petservice.repository.PetRepository;
import com.todomypet.petservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final AdoptRepository adoptRepository;
    private final UserServiceClient userServiceClient;
    private final UserRepository userRepository;
    private final PetMapper petMapper;


    @Transactional
    @Override
    public void addPet(AddPetReqDTOList addPetReqDTO) {
        for (AddPetReqDTO req : addPetReqDTO.getPetList()) {
            Pet p = Pet.builder().id(req.getId())
                    .petName(req.getName())
                    .petMaxExperiencePoint(req.getMaxExperience())
                    .petImageUrl(req.getImageUrl())
                    .petPortraitUrl(req.getPortraitUrl())
                    .petGif(req.getGif())
                    .petDescribe(req.getDescribe())
                    .petPersonality(PetPersonalityType.valueOf(req.getPersonality()))
                    .petCondition(req.getPetCondition())
                    .petType(req.getType())
                    .petGrade(req.getGrade())
                    .build();
            petRepository.save(p);
        }
    }

    @Override
    @Transactional
    public void adoptPet(String userId, AdoptPetReqDTO adoptPetReqDTO) {
        if (adoptRepository.getMainPetByUserId(userId) != null) {
            return;
        }

        if (!adoptRepository.existsAdoptByUserIdAndPetId(userId, adoptPetReqDTO.getPetId())) {
            try {
                userServiceClient.increaseCollectionCountByUserId(userId);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomException(ErrorCode.FEIGN_CLIENT_ERROR);
            }
        }

        StringBuilder signatureCode = new StringBuilder();
        Random rnd = new Random();

        while (true) {
            for (int i = 0; i < 2; i++) {
                signatureCode.append((char)(rnd.nextInt(26) + 65));
            }
            for (int i = 0; i < 9; i++) {
                signatureCode.append(rnd.nextInt(10));
            }

            if (adoptRepository.getOneAdoptBySignatureCode(signatureCode.toString()).isEmpty()) {
                break;
            }
        }

        adoptRepository.createAdoptBetweenAdoptAndUser(userId, adoptPetReqDTO.getPetId(),
                adoptPetReqDTO.getName(), UlidCreator.getUlid().toString(), signatureCode.toString(),
                adoptPetReqDTO.isRenameOrNot());

    }

    @Override
    public AdoptedPetResListDTO getAdoptedPetList(String userId) {
        List<Adopt> getAdoptList = adoptRepository.getAdoptList(userId);
        List<AdoptedPetResDTO> adoptedPetResDTOList = new ArrayList<AdoptedPetResDTO>();
        for(Adopt adopt : getAdoptList) {
            Pet pet = petRepository.getPetBySeqOfAdopt(adopt.getSeq());
            AdoptedPetResDTO adoptedPetResDTO = AdoptedPetResDTO.builder()
                    .name(adopt.getName())
                    .experiencePoint(adopt.getExperiencePoint())
                    .imageUrl(pet.getPetImageUrl())
                    .grade(pet.getPetGrade())
                    .maxExperiencePoint(pet.getPetMaxExperiencePoint())
                    .signatureCode(adopt.getSignatureCode())
                    .graduated(adopt.isGraduated())
                    .build();
            adoptedPetResDTOList.add(adoptedPetResDTO);
        }
        return AdoptedPetResListDTO.builder().petList(adoptedPetResDTOList).build();
    }

    @Override
    public GetMyPetInfoResListDTO getMyPetInfo(String userId, String signatureCode) {
        List<Adopt> petInfos = adoptRepository.getMyPetInfo(userId, signatureCode);
        List<GetMyPetInfoResDTO> getMyPetInfoResDTOList = new ArrayList<GetMyPetInfoResDTO>();
        for (Adopt adopt : petInfos) {
            Pet pet = petRepository.getPetBySeqOfAdopt(adopt.getSeq());

            GetMyPetInfoResDTO getMyPetInfoResDTO = GetMyPetInfoResDTO.builder()
                    .imageUrl(pet.getPetImageUrl())
                    .name(adopt.getName())
                    .maxExperience(pet.getPetMaxExperiencePoint())
                    .experience(adopt.getExperiencePoint())
                    .grade(pet.getPetGrade())
                    .graduated(adopt.isGraduated())
                    .seq(adopt.getSeq())
                    .build();

            getMyPetInfoResDTOList.add(getMyPetInfoResDTO);
        }
        return GetMyPetInfoResListDTO.builder().petInfoList(getMyPetInfoResDTOList).build();
    }

    @Override
    @Transactional
    public PetDetailResDTO getPetDetail(String userId, String seq) {
        Adopt adopt = adoptRepository.getAdoptBySeq(userId, seq)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ADOPT_RELATIONSHIP));
        Pet pet = petRepository.getPetBySeqOfAdopt(seq);

        return PetDetailResDTO.builder()
                .petOriginName(pet.getPetName())
                .grade(pet.getPetGrade())
                .name(adopt.getName())
                .type(pet.getPetType())
                .personality(pet.getPetPersonality())
                .description(pet.getPetDescribe())
                .imageUrl(pet.getPetImageUrl())
                .build();
    }

    @Override
    public void renamePet(String userId, RenamePetReqDTO renamePetReqDTO) {
        List<Adopt> adopt = adoptRepository.getAdoptByUserIdAndSignatureCode(userId, renamePetReqDTO.getSignatureCode());

        if (adopt.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_COLLECT_USER_AND_SIGNATURE_CODE);
        }

        adoptRepository.renamePet(userId, renamePetReqDTO.getSignatureCode(), renamePetReqDTO.getRename());
    }

    @Override
    public GetPetCollectionListResDTO getPetCollection(String userId) {
        PetType[] petTypeList = PetType.values();
        HashMap<String, List<GetPetCollectionResDTO>> collectionList = new HashMap<>();
        for (int i = 0; i < petTypeList.length; i++) {
            List<Pet> petList = petRepository.getPetList(petTypeList[i]);
            List<GetPetCollectionResDTO> getPetCollectionResList = new ArrayList<>();
            for (int j = 0; j < petList.size(); j++) {
                Pet pet = petList.get(j);
                GetPetCollectionResDTO getPetCollectionResDTO = GetPetCollectionResDTO.builder()
                        .id(pet.getId())
                        .petName(pet.getPetName())
                        .imageUrl(pet.getPetImageUrl())
                        .collected(!adoptRepository.getAdoptBetweenUserAndPet(userId, pet.getId()).isEmpty())
                        .describe(pet.getPetDescribe())
                        .personality(pet.getPetPersonality())
                        .grade(pet.getPetGrade())
                        .build();
                getPetCollectionResList.add(getPetCollectionResDTO);
            }
            collectionList.put(petTypeList[i].toString(), getPetCollectionResList);
        }
        return GetPetCollectionListResDTO.builder().collectionList(collectionList).build();
    }

    @Override
    @Transactional
    public List<CommunityPetListResDTO> getCommunityPetList(String userId) {
        List<Adopt> adoptList = adoptRepository.getCommunityPetList(userId);
        List<CommunityPetListResDTO> communityPetListResDTOList = new ArrayList<>();
        for (Adopt adopt : adoptList) {
            communityPetListResDTOList.add(CommunityPetListResDTO.builder()
                    .id(adopt.getSeq())
                    .petName(adopt.getName())
                    .petImageUrl(petRepository.getPetBySeqOfAdopt(adopt.getSeq()).getPetImageUrl()).build());

        }
        return communityPetListResDTOList;
    }

    @Override
    @Transactional
    public UpdateExperiencePointResDTO updateExperiencePoint(String userId,
                                                             UpdateExperiencePointReqDTO updateExperiencePointReqDTO) {
        log.info(">>> 경험치 획득 진입: (유저)" + userId + "/ (펫 seqId)" +
                updateExperiencePointReqDTO.getPetSeqId() + " (기존 경험치)" + adoptRepository.getExperiencePointBySeqId(userId,
                updateExperiencePointReqDTO.getPetSeqId()));
        if (adoptRepository.getAdoptBySeq(userId, updateExperiencePointReqDTO.getPetSeqId()).isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXISTS_ADOPT_RELATIONSHIP);
        };

        adoptRepository.updateExperiencePoint(userId, updateExperiencePointReqDTO.getPetSeqId(),
                updateExperiencePointReqDTO.getExperiencePoint());

        int updatedExp = adoptRepository.getExperiencePointBySeqId(userId,
                updateExperiencePointReqDTO.getPetSeqId());

        log.info(">>> 경험치 획득 완료: (유저)" + userId + "/ (펫 seqId)" +
                updateExperiencePointReqDTO.getPetSeqId() + " (갱신 경험치)" + adoptRepository.getExperiencePointBySeqId(userId,
                updateExperiencePointReqDTO.getPetSeqId()));

        return UpdateExperiencePointResDTO.builder().updatedExperiencePoint(updatedExp).build();
    }

    @Override
    public List<GetPetUpgradeChoiceResDTO> getPetUpgradeChoice(String userId, String petId) {
        Pet pet = petRepository.getPetByPetId(petId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_PET));

        PetGradeType nextGrade = getPetNextGradeType(pet);

        List<Pet> petList = petRepository.getPetByGradeAndType(nextGrade, pet.getPetType());
        List<GetPetUpgradeChoiceResDTO> response = new ArrayList<>();

        for (Pet p: petList) {
            GetPetUpgradeChoiceResDTO getPetUpgradeChoiceResDTO = GetPetUpgradeChoiceResDTO.builder()
                    .id(p.getId())
                    .petName(p.getPetName())
                    .petImageUrl(p.getPetImageUrl())
                    .petGrade(nextGrade)
                    .getOrNot(adoptRepository.existsAdoptByUserIdAndPetId(userId, p.getId()))
                    .build();
            response.add(getPetUpgradeChoiceResDTO);
        }
        return response;
    }

    @Override
    @Transactional
    public UpgradePetResDTO evolvePet(String userId, UpgradePetReqDTO req) {
        log.info(">>> 펫 진화 진입: (userId)" + userId + " " + "(펫 signatureCode)" + req.getSignatureCode());

        if (!adoptRepository.existsAdoptByUserIdAndPetId(userId, req.getSelectedPetId())) {
            int collectionCount = userServiceClient.increaseCollectionCountByUserId(userId).getData();
        }

        Adopt adopt = adoptRepository.getAdoptBySeq(userId, req.getSeq())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ADOPT_RELATIONSHIP));
        adoptRepository.graduatePetBySeq(userId, adopt.getSeq());
        if (adoptRepository.getMainPetByUserId(userId) == null) {
            String originName = req.getPetName();
            String currentName = req.getPetName();
            Pet pet = petRepository.getPetBySeqOfAdopt(req.getSeq());

            if (adopt.getExperiencePoint() < pet.getPetMaxExperiencePoint()) {
                throw new CustomException(ErrorCode.EXPERIENCE_POINT_NOT_SATISFIED);
            }

            if (adopt.isRenameOrNot()) {
                log.info(">>> (" + userId + ") rename check");
                originName = pet.getPetName();
            }

            Pet selectedPet = petRepository.getPetByPetId(req.getSelectedPetId()).orElseThrow(()
                    -> new CustomException(ErrorCode.NOT_EXISTS_PET));
            String newName = selectedPet.getPetName();

            if (adopt.isRenameOrNot()) {
                adoptRepository.createAdoptBetweenAdoptAndUser(userId, req.getSelectedPetId(), currentName,
                        UlidCreator.getUlid().toString(), adopt.getSignatureCode(), adopt.isRenameOrNot());
            } else {
                adoptRepository.createAdoptBetweenAdoptAndUser(userId, req.getSelectedPetId(), newName,
                        UlidCreator.getUlid().toString(), adopt.getSignatureCode(), adopt.isRenameOrNot());
            }

            int evolveCount = userServiceClient.increaseAndGetPetEvolveCountByUserId(userId).getData();

            return UpgradePetResDTO.builder().renameOrNot(adopt.isRenameOrNot()).originName(originName)
                    .currentName(currentName).selectPetOriginName(newName)
                    .achCondition(evolveCount).petImageUrl(selectedPet.getPetImageUrl()).build();
        }
        return null;
    }

    @Override
    @Transactional
    public GraduatePetResDTO graduatePet(String userId, GraduatePetReqDTO req) {
        log.info(">>> 펫 졸업 진입: (userId)" + userId + " " + "(펫 seq)" + req.getPetSeq());

        Adopt adopt = adoptRepository.getAdoptBySeq(userId, req.getPetSeq())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXISTS_ADOPT_RELATIONSHIP));

        Pet pet = petRepository.getPetBySeqOfAdopt(req.getPetSeq());

        if (pet.getPetGrade() != PetGradeType.ADULT) {
            throw new CustomException(ErrorCode.GRADUATION_MUST_BE_ADULT_GRADE);
        }

        adoptRepository.graduatePetBySeq(userId, req.getPetSeq());

        int condition = userServiceClient.increaseAndGetPetCompleteCountByUserId(userId).getData();

        return GraduatePetResDTO.builder().petName(adopt.getName())
                .petImageUrl(pet.getPetImageUrl()).achCondition(condition).build();
    }

    @Override
    public String getMainPetSeqByUserId(String userId) {
        Adopt adopt = adoptRepository.getMainPetByUserId(userId);

        if (adopt == null) {
            return null;
        } else {
            return adoptRepository.getMainPetByUserId(userId).getSeq();
        }
    }

    @Override
    public List<GetAvailableStartingPetDTO> getAvailableStartingPet(String userId) {
        List<Pet> petList = petRepository.getAvailablePet(userId);
        List<GetAvailableStartingPetDTO> response = new ArrayList<>();
        for (Pet p : petList) {
            response.add(petMapper.petToGetAvailableStartingPetDTO(p));
        }
        return response;
    }

    @Override
    public GetMainPetInfosResDTO getMainPetInfosByUserId(String userId) {
        Adopt adopt = adoptRepository.getMainPetByUserId(userId);

        if (adopt == null) {
            throw new CustomException(ErrorCode.NOT_EXISTS_MAIN_PET);
        }

        Pet pet = petRepository.getPetBySeqOfAdopt(adopt.getSeq());
        return GetMainPetInfosResDTO.builder().petGrade(String.valueOf(pet.getPetGrade())).petPortraitImageUrl(pet.getPetPortraitUrl())
                .petGifUrl(pet.getPetGif()).petName(adopt.getName()).petExperiencePoint(adopt.getExperiencePoint())
                .petMaxExperiencePoint(pet.getPetMaxExperiencePoint()).petPersonalityType(pet.getPetPersonality())
                .petId(pet.getId()).petSignatureCode(adopt.getSignatureCode()).petSeq(adopt.getSeq()).build();
    }

    @Override
    public GetPetLinesResDTO getPetLines() {

        Map<String, String> resign = new HashMap<>();
        resign.put("GLUTTON", "속상해서 입맛이 없어졌어...");
        resign.put("CALM", "내를... 떠날기가...?");
        resign.put("CHEERFUL", "정말 저를 떠나실 건가요?");
        resign.put("PROTEIN", "그동안 고마웠다. 건강해.");
        resign.put("PURE_EVIL", "어딜 간다구요...?");

        Map<String, String> firstMeet = new HashMap<>();
        firstMeet.put("GLUTTON", "만반잘부~");
        firstMeet.put("CALM", "만나서 반갑심더");
        firstMeet.put("CHEERFUL", "잘 부탁드려요!");
        firstMeet.put("PROTEIN", "반갑다");
        firstMeet.put("PURE_EVIL", "헤헤, 반가워요?");

        Map<String, String> fullGuage = new HashMap<>();
        fullGuage.put("GLUTTON", "몸이 이상해~ 뭘 잘못 먹었나?");
        fullGuage.put("CALM", "와... 와이라노...?");
        fullGuage.put("CHEERFUL", "어어? 뭔가 이상한 기분이에요~");
        fullGuage.put("PROTEIN", "몸이 이상하군 벌크업인가");
        fullGuage.put("PURE_EVIL", "어머, 기대되는걸요?");

        Map<String, String> upgrade = new HashMap<>();
        upgrade.put("GLUTTON", "앗, 너무 많이 먹어 버렸나 봐!");
        upgrade.put("CALM", "나 진화해 뿟다");
        upgrade.put("CHEERFUL", "더 멋진 모습으로 만나서 기뻐요!");
        upgrade.put("PROTEIN", "고맙다");
        upgrade.put("PURE_EVIL", "나 계속 예뻐해 줄 거죠?");

        Map<String, List<String>> mainPageRandomLine = new HashMap<>();
        mainPageRandomLine.put("GLUTTON", Arrays.asList(
                "할 일이 많을 때는 하나씩 차근차근!",
                "화이팅!!",
                "너무 멋져!",
                "이거 비밀인데, 넌 내 자랑이야!",
                "좋아해!",
                "맛있는 음식은 생각만으로 신나",
                "오늘 맛있는 거 먹었어?",
                "오늘은 뭐해?",
                "같이 맛집 투어 하고 싶어!",
                "네가 어떤 모습이든 좋아!",
                "세상엔 기분 좋은 일이 많아!",
                "취미가 궁금해. 나는 맛집투어!",
                "비슷해 보여도 매일 매일 다르네!",
                "헤헤, 반가와",
                "아싸~ 신나는 하루!"
        ));
        mainPageRandomLine.put("CALM", Arrays.asList(
                "괘안타. 할 수 있을 기다.",
                "힘내라 안카나",
                "아따 멋지다",
                "일케 멋져가 우짜노",
                "내 니 마이 좋아한다...",
                "마이 뭇나? 마이 무라...",
                "고마 됐다",
                "만다꼬 이래 왔나?",
                "우짜노 우야면 좋노...",
                "아따... 니가 와따다",
                "우짜긋노...",
                "나도 힘낼끼다...",
                "괘안타... 니는 괘안나",
                "밥 뭇나?",
                "단디 해라"
        ));
        mainPageRandomLine.put("CHEERFUL", Arrays.asList(
                "할 수 있어요! 화이팅이에요.",
                "오늘도 화이팅이에요!",
                "어제보다 오늘 더 성장했어요!",
                "언제나 멋져요!",
                "오늘 하루도 행복하게 보내요",
                "뭔가 즐거운 일이 생길 것만 같아요!",
                "고민이 많을 때는 저한테 나눠 줘요",
                "오늘은 특별한 날이 될 것 같아요",
                "함께라면 뭐든 즐거워요",
                "제게는 언제나 최고예요!",
                "힘들 땐 같이 웃어 봐요",
                "즐거운 일도, 힘든 일도 함께해요",
                "심심하면 절 불러 주세요",
                "만날 때마다 행복한걸요?",
                "매일이 즐거워요!"
        ));
        mainPageRandomLine.put("PROTEIN", Arrays.asList(
                "할 수 있다",
                "조금만 더 힘내도록",
                "내가 다 뿌듯하구만",
                "자랑스럽군",
                "좋은 하루 돼라",
                "잘 먹고 다녀",
                "건강한 몸에 건강한 정신이!",
                "별일 없나? 다행이군...",
                "같이 운동하러 가지 않겠나?",
                "최고다!",
                "힘들어? 나한테 말해봐",
                "힘들 땐 쉬면서 해라. 건강이 최고다.",
                "다 울었나? 이제 할일을 하자",
                "봐도 봐도 반갑군. 신기해.",
                "널 위해선 근손실도 참을 수 있어"
        ));
        mainPageRandomLine.put("PURE_EVIL", Arrays.asList(
                "할 수 있어요. 그렇지?",
                "너무 좋아요. 나랑 계속 있어.",
                "역시 멋지네요?",
                "당신은 나를 떠날 수 없어요...",
                "내가 있어서 역시나 좋은 하루죠?",
                "오늘도 날 찾았군요, 헤헤",
                "계속 계속 보고 싶어",
                "무서운 일은 없는 거죠?",
                "나만 믿어요. 무서워하지 말고요.",
                "우리는 항상 함께해야해요",
                "나 없이 행복하지 말아요",
                "너의 모든 걸 알고 싶어...",
                "사랑한다고 말해 줘요",
                "나 달라진 거 없어? 저 좀 봐 줘요",
                "같이 있어요. 영원히..."
        ));

        return GetPetLinesResDTO.builder().resign(resign).firstMeet(firstMeet).fullGauge(fullGuage).upgrade(upgrade)
                .mainPageRandomLine(mainPageRandomLine).build();
    }

    private static PetGradeType getPetNextGradeType(Pet pet) {
        PetGradeType grade = pet.getPetGrade();

        PetGradeType newGrade = null;
        switch (grade) {
            case BABY -> {
                newGrade = PetGradeType.CHILDREN;
            }
            case CHILDREN -> {
                newGrade = PetGradeType.TEENAGER;
            }
            case TEENAGER -> {
                newGrade = PetGradeType.ADULT;
            }
            default -> {
                throw new CustomException(ErrorCode.USER_NOT_EXISTS);
            }
        }
        return newGrade;
    }

//    public void earnAchievement(User user, AchievementType achievementType, int achievementCondition) {
//        Achievement achievement = achievementRepository
//                .isSatisfyAchievementCondition(achievementType, achievementCondition);
//        if (achievement != null) {
//            achieveRepository.createAchieveBetweenUserAndAchievement(user.getId(), achievement.getId(),
//                    String.valueOf(LocalDateTime.parse(DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss")
//                            .format(LocalDateTime.now()))));
//        };
//    };
}
