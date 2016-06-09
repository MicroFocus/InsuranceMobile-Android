/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import silktest.insurancemobile.model.Agent;

public class AgentDetailFragment extends Fragment {
  Agent agent;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    int agentId = getArguments().getInt("id");
    agent = Agent.getAgentById(agentId);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.agent_details, container, false);

    ((TextView) rootView.findViewById(R.id.agent_name)).setText(agent.toString());
    ((TextView) rootView.findViewById(R.id.agent_address)).setText(agent.getAddress());
    ((TextView) rootView.findViewById(R.id.agent_state)).setText(agent.getState());
    ((TextView) rootView.findViewById(R.id.agent_phone)).setText(agent.getPhone());

    rootView.findViewById(R.id.agent_details_close).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
                .hide(AgentDetailFragment.this)
                .commit();
      }
    });

    return rootView;
  }
}
