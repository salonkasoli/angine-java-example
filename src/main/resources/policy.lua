local lib = require('angine/alfa')
local __test = lib.test
local __subseteq = lib.subseteq
local __issubseteq = lib.issubseteq
local __iselement = lib.iselement
local __isany = lib.isany

-- example namespace begin
local example = {}
local __example = function(example)
    -- mainPolicy policy set begin
    function example.mainPolicy(ctx, actions, handlers)
        -- getIndex policy begin
        local function getIndex(ctx, actions, handlers)
            -- target begin
            if not ctx.action or not ctx.entity.path then
                return actions.indeterminate
            end
            if not ( ctx.action == "GET" and ctx.entity.path == "/index" ) then
                return actions.notapplicable
            end
            -- target end

            -- r1 rule begin
            local function r1(ctx, actions, handlers)
                if ( true ) then
                    return actions.permit
                end
                return actions.notapplicable
            end
            -- r1 rule end


            -- denyunlesspermit rule-combining algorithm begin
            local rules = { r0 = r1 }
            for _, rule in pairs(rules) do
                local decision = rule(ctx, actions, handlers)
                if decision == actions.permit then
                    return actions.permit
                end
            end
            return actions.deny
            -- denyunlesspermit rule-combining algorithm end

        end
        -- getIndex policy end

        -- getSlash policy begin
        local function getSlash(ctx, actions, handlers)
            -- target begin
            if not ctx.action or not ctx.entity.path then
                return actions.indeterminate
            end
            if not ( ctx.action == "GET" and ctx.entity.path == "/" ) then
                return actions.notapplicable
            end
            -- target end

            -- r1 rule begin
            local function r1(ctx, actions, handlers)
                if ( true ) then
                    return actions.permit
                end
                return actions.notapplicable
            end
            -- r1 rule end


            -- denyunlesspermit rule-combining algorithm begin
            local rules = { r0 = r1 }
            for _, rule in pairs(rules) do
                local decision = rule(ctx, actions, handlers)
                if decision == actions.permit then
                    return actions.permit
                end
            end
            return actions.deny
            -- denyunlesspermit rule-combining algorithm end

        end
        -- getSlash policy end

        -- postAuth policy begin
        local function postAuth(ctx, actions, handlers)
            -- target begin
            if not ctx.action or not ctx.entity.path then
                return actions.indeterminate
            end
            if not ( ctx.action == "POST" and ctx.entity.path == "/auth" ) then
                return actions.notapplicable
            end
            -- target end

            -- r1 rule begin
            local function r1(ctx, actions, handlers)
                if ( true ) then
                    return actions.permit
                end
                return actions.notapplicable
            end
            -- r1 rule end


            -- denyunlesspermit rule-combining algorithm begin
            local rules = { r0 = r1 }
            for _, rule in pairs(rules) do
                local decision = rule(ctx, actions, handlers)
                if decision == actions.permit then
                    return actions.permit
                end
            end
            return actions.deny
            -- denyunlesspermit rule-combining algorithm end

        end
        -- postAuth policy end

        -- getLogin policy begin
        local function getLogin(ctx, actions, handlers)
            -- target begin
            if not ctx.action or not ctx.entity.path then
                return actions.indeterminate
            end
            if not ( ctx.action == "GET" and ctx.entity.path == "/login" ) then
                return actions.notapplicable
            end
            -- target end

            -- r1 rule begin
            local function r1(ctx, actions, handlers)
                if ( true ) then
                    return actions.permit
                end
                return actions.notapplicable
            end
            -- r1 rule end


            -- denyunlesspermit rule-combining algorithm begin
            local rules = { r0 = r1 }
            for _, rule in pairs(rules) do
                local decision = rule(ctx, actions, handlers)
                if decision == actions.permit then
                    return actions.permit
                end
            end
            return actions.deny
            -- denyunlesspermit rule-combining algorithm end

        end
        -- getLogin policy end

        -- getAdmin policy begin
        local function getAdmin(ctx, actions, handlers)
            -- target begin
            if not ctx.action or not ctx.entity.path then
                return actions.indeterminate
            end
            if not ( ctx.action == "GET" and ctx.entity.path == "/admin" ) then
                return actions.notapplicable
            end
            -- target end

            -- r1 rule begin
            local function r1(ctx, actions, handlers)
                if not ctx.subject.role then
                    return actions.indeterminate
                end
                if ( ctx.subject.role == "admin" ) then
                    return actions.permit
                end
                return actions.notapplicable
            end
            -- r1 rule end


            -- denyunlesspermit rule-combining algorithm begin
            local rules = { r0 = r1 }
            for _, rule in pairs(rules) do
                local decision = rule(ctx, actions, handlers)
                if decision == actions.permit then
                    return actions.permit
                end
            end
            return actions.deny
            -- denyunlesspermit rule-combining algorithm end

        end
        -- getAdmin policy end


        -- denyunlesspermit policy-combining algorithm begin
        local policies = { p0 = getIndex, p1 = getSlash, p2 = postAuth, p3 = getLogin, p4 = getAdmin }
        for _, policy in pairs(policies) do
            local decision = policy(ctx, actions, handlers)
            if decision == actions.permit then
                return actions.permit
            end
        end
        return actions.deny
        -- denyunlesspermit policy-combining algorithm end

    end
    -- example.mainPolicy policy set end

end
__example(example)
-- example namespace end

function __main(ctx, actions, handlers)
    return example.mainPolicy(ctx, actions, handlers)
end