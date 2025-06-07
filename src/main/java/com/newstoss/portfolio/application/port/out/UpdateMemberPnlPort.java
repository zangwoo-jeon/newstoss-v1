package com.newstoss.portfolio.application.port.out;

import java.util.UUID;

public interface UpdateMemberPnlPort {
    void updateMemberPnl(UUID memberId , Integer pnl, Long asset);
}
