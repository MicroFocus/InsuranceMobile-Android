/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import silktest.insurancemobile.model.Agent;

public class ShowAgentsActivity extends BackNavigatingActivity {
  private View progressbar;
  private View content;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_show_agents);

    setupToolbar();

    progressbar = findViewById(R.id.progressbar);
    content = findViewById(R.id.layout_content);

    showProgress(true);
    getAndShowAgents();
    showProgress(false);
  }

  private void getAndShowAgents() {
    final List<Agent> agents = new ArrayList<>();

    String location = getIntent().getStringExtra("location");
    String name = getIntent().getStringExtra("name");
    if (!TextUtils.isEmpty(location)) {
      agents.addAll(Agent.getAgentsByZipCode(location));
    } else if (!TextUtils.isEmpty(name)) {
      agents.addAll(Agent.getAgentsByName(name));
    } else {
      agents.addAll(Agent.getAllAgents());
    }

    TextView status = (TextView) findViewById(R.id.agent_search_status);
    String count = new Integer(agents.size()).toString();

    if (agents.isEmpty()) {
      count = "No";
    }

    status.setText(String.format("%s agent%s found", count, agents.size() == 1 ? "" : "s"));

    ListView listView = (ListView) findViewById(R.id.listView);

    listView.setAdapter(new ArrayAdapter<Agent>(this, R.layout.agent_list_item, agents) {
    });

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.agent_detail_container);

        if (fragment != null) {
          getSupportFragmentManager().beginTransaction()
                  .remove(fragment)
                  .commit();
        }

        Agent agent = agents.get(position);

        Bundle arguments = new Bundle();
        arguments.putInt("id", agent.getId());

        fragment = new AgentDetailFragment();
        fragment.setArguments(arguments);

        if (fragment != null) {
          getSupportFragmentManager().beginTransaction()
                  .setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
                  .replace(R.id.agent_detail_container, fragment)
                  .commit();

        } else {
          getSupportFragmentManager().beginTransaction()
                  .setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
                  .add(R.id.agent_detail_container, fragment)
                  .commit();
        }
      }
    });
  }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
  private void showProgress(final boolean show) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

      content.setVisibility(show ? View.GONE : View.VISIBLE);
      progressbar.setVisibility(show ? View.VISIBLE : View.GONE);
      progressbar.animate().setDuration(shortAnimTime).alpha(
              show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
          progressbar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
      });
    } else {
      progressbar.setVisibility(show ? View.VISIBLE : View.GONE);
      content.setVisibility(show ? View.VISIBLE : View.GONE);
    }
  }
}
