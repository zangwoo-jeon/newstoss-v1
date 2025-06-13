package com.newstoss.portfolio.application.port.in;

import java.util.UUID;

public interface UpdateMemberPnlUseCase {
    void updateTodayPnl(UUID memberId);
}
