package com.newstoss.portfolio.application.port.out;

import com.newstoss.portfolio.entity.MemberPnl;

public interface CreateMemberPnlPort {
    Long create(MemberPnl memberPnl);
}
