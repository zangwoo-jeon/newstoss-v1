package com.newstoss.member.domain.event;

import java.util.UUID;

public record MemberSignUpEvent(UUID memberId) {
}
