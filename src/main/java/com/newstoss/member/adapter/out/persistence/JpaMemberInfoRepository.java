package com.newstoss.member.adapter.out.persistence;

import com.newstoss.member.adapter.in.web.dto.response.MemberInfoDto;

import java.util.UUID;

public interface JpaMemberInfoRepository {
    MemberInfoDto findMemberInfo(UUID id);
}
